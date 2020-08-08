package net.achris.mediumnothanks.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.ui.home.HomeFragment
import net.achris.mediumnothanks.ui.web.WebViewFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.setInitialColorMode(applyAppTheme = true)
        val rawLink = intent?.getStringExtra(Intent.EXTRA_TEXT)
        if (rawLink != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, WebViewFragment.newInstance(rawLink))
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, HomeFragment())
                .commit()
        }
    }

}