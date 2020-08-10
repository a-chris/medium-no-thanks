package net.achris.mediumnothanks.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.extensions.animateFlipY
import net.achris.mediumnothanks.extensions.flipIn
import net.achris.mediumnothanks.extensions.playAnimation
import net.achris.mediumnothanks.model.ColorMode
import net.achris.mediumnothanks.ui.activity.ActivityViewModel
import net.achris.mediumnothanks.util.GITHUB_PROJECT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class FragmentWithToolbar : Fragment() {

    protected val viewModel: ActivityViewModel by sharedViewModel()

    private val colorModeMenuItem by lazy {
        requireView().findViewById<View>(R.id.action_color_mode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
            To be applied after the color mode change
         */
        if (viewModel.currentColorModel == ColorMode.DARK) {
            colorModeMenuItem.flipIn()
        }
    }

    /*
        To be called during onCreateView()
     */
    protected fun setupToolbar(refreshAppOnThemeChange: Boolean) {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_color_mode ->
                    colorModeMenuItem.animateFlipY()
                        .playAnimation()
                        .doOnEnd {
                            toggleTheme(refreshAppOnThemeChange)
                        }
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