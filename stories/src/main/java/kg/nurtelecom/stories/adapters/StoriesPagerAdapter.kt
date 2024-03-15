package kg.nurtelecom.stories.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.nurtelecom.stories.models.Story
import kg.nurtelecom.stories.StoryCallbackListener
import kg.nurtelecom.stories.StoryFragment

class StoriesPagerAdapter(
    fragmentActivity: FragmentActivity,
    private var storiesList: List<Story>,
    private val callbackListener: StoryCallbackListener
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return storiesList.size
    }

    override fun createFragment(position: Int): Fragment {
        return StoryFragment.newInstance(storiesList[position], callbackListener)
    }
}
