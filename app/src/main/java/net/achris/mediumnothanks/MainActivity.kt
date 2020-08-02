package net.achris.mediumnothanks

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.achris.mediumnothanks.data.MEDIUM_BASE_LINK

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.onLoadingStarted = { progress.visibility = View.VISIBLE }
        webview.onLoadingFinished = { progress.visibility = View.GONE }

        val receivedLink = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (receivedLink == null) {
            welcome_title.visibility = View.VISIBLE
            instructions_title.visibility = View.VISIBLE
            instructions_container.visibility = View.VISIBLE
            Toast.makeText(this, getString(R.string.error_no_link), Toast.LENGTH_LONG).show()
        } else {
            welcome_title.visibility = View.GONE
            instructions_title.visibility = View.GONE
            instructions_container.visibility = View.GONE
            webview.visibility = View.VISIBLE
            val articleTitle = receivedLink.substringBefore(MEDIUM_BASE_LINK).trimEnd()
            val mediumUrl = MEDIUM_BASE_LINK + receivedLink.substringAfterLast(MEDIUM_BASE_LINK)
            Toast.makeText(this, articleTitle, Toast.LENGTH_LONG).show()
            webview.loadUrl(mediumUrl)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}