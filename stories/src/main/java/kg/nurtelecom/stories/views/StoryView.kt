package kg.nurtelecom.stories.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import kg.nurtelecom.stories.StoryCallbackListener
import kg.nurtelecom.stories.adapters.HighlightsAdapter
import kg.nurtelecom.stories.adapters.OnHighlightClickListener
import kg.nurtelecom.stories.adapters.StoriesPagerAdapter
import kg.nurtelecom.stories.databinding.ViewStoriesBinding
import kg.nurtelecom.stories.models.Story

class StoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), OnHighlightClickListener, StoryCallbackListener {

    private var vb: ViewStoriesBinding =
        ViewStoriesBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var viewPager: ViewPager2
    private lateinit var contextFragmentActivity: FragmentActivity

    private var stories = emptyList<Story>()
    private var currentIndex: Int = 0
    private var currentClickedView: View? = null

    fun setup(fragmentActivity: FragmentActivity, stories: List<Story>) {
        contextFragmentActivity = fragmentActivity
        this.stories = stories
        viewPager = vb.viewPager

        val highlightsAdapter = HighlightsAdapter(this)
        highlightsAdapter.addItems(stories)
        vb.rvHighlights.adapter = highlightsAdapter
    }

    override fun onHighlightClick(clickedView: View, index: Int) {
        currentIndex = index
        currentClickedView = clickedView
        initPager()
        showStory(clickedView)
    }

    private fun initPager() {
        val storiesPagerAdapter = StoriesPagerAdapter(contextFragmentActivity, stories, this)
        viewPager.apply {
            isVisible = true
            adapter = storiesPagerAdapter
            offscreenPageLimit = 7
            setCurrentItem(currentIndex, false)
        }
    }

    override fun closingStory() {
        vb.rvHighlights.apply {
            smoothScrollToPosition(viewPager.currentItem)
            postDelayed({
                updateCurrentStoryView()
                currentClickedView?.let { closeStory(it) }
                currentClickedView = null
            }, 100)
        }
    }

    override fun viewPagerSwipes(isEnabled: Boolean) {
        viewPager.isUserInputEnabled = isEnabled
    }

    override fun onAllStoriesViewClick() {
        Toast.makeText(context, "Переход на экран событий!", Toast.LENGTH_SHORT).show()
    }

    override fun goToNextFragment() {
        updateCurrentStoryView()
        if (viewPager.currentItem + 1 < stories.size)
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        else closingStory()
    }

    override fun goToPrevFragment() {
        updateCurrentStoryView()
        if (viewPager.currentItem > 0)
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }

    private fun updateCurrentStoryView() =
        vb.rvHighlights.findViewHolderForAdapterPosition(viewPager.currentItem)?.let {
            currentClickedView = it.itemView
        }

    private fun showStory(clickedView: View, duration: Long = 310L) {
        val location = IntArray(2).apply { clickedView.getLocationOnScreen(this) }
        viewPager.apply {
            pivotX = 0f
            pivotY = 0f
            scaleX = if (width > 0) clickedView.width.toFloat() / width else 1f
            scaleY = if (height > 0) clickedView.height.toFloat() / height else 1f
            translationX = location[0].toFloat()
            translationY = location[1].toFloat()
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0f)
                .translationY(0f)
                .setDuration(duration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }

    private fun closeStory(clickedView: View, duration: Long = 180L) {
        val location = IntArray(2).apply { clickedView.getLocationOnScreen(this) }
        viewPager.animate()
            .scaleX(clickedView.width.toFloat() / width)
            .scaleY(clickedView.height.toFloat() / height)
            .translationX(location[0].toFloat())
            .translationY((location[1] / 2).toFloat())
            .setDuration(duration)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction { viewPager.isVisible = false }
            .start()
    }

    override fun openDeeplink(deeplink: String) {
        Toast.makeText(context, deeplink, Toast.LENGTH_LONG).show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewPager.adapter = null
        stories = emptyList()
    }
}
