package kg.nurtelecom.stories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.nurtelecom.stories.views.StoryView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<StoryView>(R.id.cv_story_view).setup(supportFragmentManager,this, Mocks.stories)
    }
}