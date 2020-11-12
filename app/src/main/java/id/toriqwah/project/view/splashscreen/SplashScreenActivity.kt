package id.toriqwah.project.view.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.toriqwah.project.R
import id.toriqwah.project.view.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setSplashScreen()
    }

    private fun setSplashScreen(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },5000)
    }
}