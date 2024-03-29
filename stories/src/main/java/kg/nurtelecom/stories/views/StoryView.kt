package kg.nurtelecom.stories.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import kg.nurtelecom.stories.DialogCloseListener
import kg.nurtelecom.stories.StoriesDialogFragment
import kg.nurtelecom.stories.adapters.HighlightsAdapter
import kg.nurtelecom.stories.adapters.OnHighlightClickListener
import kg.nurtelecom.stories.databinding.ViewStoriesBinding
import kg.nurtelecom.stories.models.Story

class StoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), OnHighlightClickListener,
    DialogCloseListener {

    private var vb: ViewStoriesBinding =
        ViewStoriesBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var contextFragmentActivity: FragmentActivity
    private lateinit var fragmentManager: FragmentManager

    private var stories = emptyList<Story>()
    private var currentIndex: Int = 0
    private var currentClickedView: View? = null

    fun setup(
        fragmentManager: FragmentManager,
        fragmentActivity: FragmentActivity,
        stories: List<Story>
    ) {
        contextFragmentActivity = fragmentActivity
        this.fragmentManager = fragmentManager
        this.stories = stories

        val highlightsAdapter = HighlightsAdapter(this)
        highlightsAdapter.addItems(stories)
        vb.rvHighlights.adapter = highlightsAdapter
    }

    override fun onHighlightClick(clickedView: View, index: Int) {
        currentIndex = index
        currentClickedView = clickedView
        StoriesDialogFragment.newInstance(
            fragmentManager = fragmentManager,
            contextFragmentActivity = contextFragmentActivity,
            clickedIndex = index,
            clickedView = clickedView,
            stories = stories,
            listener = this
        )
    }

    override fun getCurrentStoryView(currentIndex: Int): View? {
        return vb.rvHighlights.findViewHolderForAdapterPosition(currentIndex)?.itemView
    }

    override fun scrollRecycler(currentIndex: Int) {
        vb.rvHighlights.smoothScrollToPosition(currentIndex)
    }

    override fun onAllStoriesViewClick() {
        Toast.makeText(context, "Переход на экран событий!", Toast.LENGTH_SHORT).show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stories = emptyList()
    }
}
