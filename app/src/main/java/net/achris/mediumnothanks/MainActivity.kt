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

        val rawLink = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (rawLink == null) {
            setInstructionsVisibility(View.VISIBLE)
            webview.visibility = View.GONE
            Toast.makeText(this, getString(R.string.error_no_link), Toast.LENGTH_LONG).show()
        } else {
            setInstructionsVisibility(View.GONE)
            webview.visibility = View.VISIBLE
            getMediumTitle(rawLink)?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
            webview.loadUrl(getMediumLink(rawLink))
        }
    }

    private fun getMediumLink(rawLink: String): String {
        return if (rawLink.contains(MEDIUM_BASE_LINK)) {
            MEDIUM_BASE_LINK + rawLink.substringAfterLast(MEDIUM_BASE_LINK)
        } else {
            rawLink
        }
    }

    private fun getMediumTitle(rawLink: String): String? {
        return if (rawLink.contains(MEDIUM_BASE_LINK)) {
            rawLink.substringBefore(MEDIUM_BASE_LINK).trimEnd()
        } else {
            null
        }
    }

    private fun setInstructionsVisibility(visibility: Int) {
        welcome_title.visibility = visibility
        instructions_title.visibility = visibility
        instructions_container.visibility = visibility
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}