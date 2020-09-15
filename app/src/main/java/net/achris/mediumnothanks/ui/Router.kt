package net.achris.mediumnothanks.ui

import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.ui.activity.MainActivity
import net.achris.mediumnothanks.ui.home.HomeFragment
import net.achris.mediumnothanks.ui.web.WebViewFragment

class Router {
    lateinit var activity: MainActivity

    fun showHome() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, HomeFragment.newInstance(), HomeFragment.TAG)
            .commit()
    }

    fun showArticle(rawUrl: String? = null) {
        if (activity.supportFragmentManager.findFragmentByTag(WebViewFragment.TAG) != null) {
            /*
                Does not replace the fragment if it is already visible
             */
            return
        }
        val webViewFragment =
            if (rawUrl == null) WebViewFragment.newInstance()
            else WebViewFragment.newInstance(rawUrl)
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, webViewFragment, WebViewFragment.TAG)
            .commit()
    }
}