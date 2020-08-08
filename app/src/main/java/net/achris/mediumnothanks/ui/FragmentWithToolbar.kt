package net.achris.mediumnothanks.ui

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.ui.activity.ActivityViewModel
import net.achris.mediumnothanks.util.GITHUB_PROJECT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class FragmentWithToolbar : Fragment() {

    protected val viewModel :ActivityViewModel by sharedViewModel()

    /*
        Call this during onCreateView()
     */
    protected fun setupToolbar(refreshAppOnThemeChange: Boolean) {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_color_mode -> toggleTheme(refreshAppOnThemeChange)
                R.id.action_github -> openGitHub()
            }
            true
        }
    }

    protected fun openGitHub() {
        val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_PROJECT))
        startActivity(openBrowserIntent)
    }

    private fun toggleTheme(refreshApp: Boolean) {
        viewModel.toggleColorMode()
        if (refreshApp) viewModel.applyAppTheme()
    }

}