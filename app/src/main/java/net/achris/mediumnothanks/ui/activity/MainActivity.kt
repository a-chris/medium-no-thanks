package net.achris.mediumnothanks.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.ui.Router
import net.achris.mediumnothanks.ui.home.HomeFragment
import net.achris.mediumnothanks.ui.web.WebViewFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val router: Router by inject()
    private val viewModel: ThemeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectActivity()

        viewModel.setInitialColorMode(applyAppTheme = true)

        val rawLink = intent?.getStringExtra(Intent.EXTRA_TEXT)
        if (rawLink != null)
            router.showArticle(rawLink)
        else
            router.showHome()
    }

    private fun injectActivity() {
        router.activity = this
    }
}
