package kg.nurtelecom.stories

interface StoryCallbackListener {
    fun closingStory()
    fun viewPagerSwipes(isEnabled: Boolean)
    fun openDeeplink(deeplink: String)
    fun goToNextFragment()
    fun goToPrevFragment()
}