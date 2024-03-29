package kg.nurtelecom.stories

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import kg.nurtelecom.stories.adapters.StoriesPagerAdapter
import kg.nurtelecom.stories.models.Story

class StoriesDialogFragment : DialogFragment(), StoryCallbackListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var contextFragmentActivity: FragmentActivity

    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex: Int = 0
    private var clickedView: View? = null
    private var stories = emptyList<Story>()
    private var dialogCloseListener: DialogCloseListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stories_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.viewPager)
        setupViewPager()
        showStory(clickedView = clickedView)
    }

    private fun setupViewPager() {
        val adapter = StoriesPagerAdapter(contextFragmentActivity, stories, this)
        viewPager.apply {
            this.adapter = adapter
            offscreenPageLimit = 7
            setCurrentItem(currentIndex, false)
        }
    }

    private fun showStory(clickedView: View?, duration: Long = 310L) {
        clickedView?.let {
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
    }

    override fun closingStory() {
        dialogCloseListener?.recyclerScroll(viewPager.currentItem)
        handler.postDelayed({
            dialogCloseListener?.getCurrentStoryView(viewPager.currentItem)?.let { closeStory(it) }
        }, 100)
    }

    private fun closeStory(clickedView: View, duration: Long = 180L) {
        val location = IntArray(2).apply { clickedView.getLocationOnScreen(this) }
        viewPager.animate()
            .scaleX(clickedView.width.toFloat() / viewPager.width)
            .scaleY(clickedView.height.toFloat() / viewPager.height)
            .translationX(location[0].toFloat())
            .translationY((location[1] / 2).toFloat())
            .setDuration(duration)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction { dismiss() }
            .start()
    }

    override fun viewPagerSwipes(isEnabled: Boolean) {
        viewPager.isUserInputEnabled = isEnabled
    }

    override fun openDeeplink(deeplink: String) {
        Toast.makeText(context, deeplink, Toast.LENGTH_LONG).show()
    }

    override fun goToNextFragment() {
        if (viewPager.currentItem + 1 < stories.size)
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        else closingStory()
    }

    override fun goToPrevFragment() {
        if (viewPager.currentItem > 0)
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        fun newInstance(fragmentManager: FragmentManager, contextFragmentActivity: FragmentActivity, clickedIndex: Int, clickedView: View, stories: List<Story>, listener: DialogCloseListener) {
           StoriesDialogFragment().apply {
                this.contextFragmentActivity = contextFragmentActivity
                this.clickedView = clickedView
                this.currentIndex = clickedIndex
                this.stories = stories
                this.dialogCloseListener = listener
            }.show(fragmentManager, "")
        }
    }
}

interface DialogCloseListener {
    fun getCurrentStoryView(currentIndex: Int): View?
    fun recyclerScroll(currentIndex: Int)
}