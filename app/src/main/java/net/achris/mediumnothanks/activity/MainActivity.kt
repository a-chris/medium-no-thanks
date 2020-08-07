package net.achris.mediumnothanks.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.ui.webview.WebViewFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[ActivityViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rawLink = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (rawLink != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, WebViewFragment.newInstance(rawLink))
                .commit()
        }

        viewModel.setColorMode(ColorMode.LIGHT)

        viewModel.getActivityState().observe(this, Observer { state ->
            Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
        })
    }

}