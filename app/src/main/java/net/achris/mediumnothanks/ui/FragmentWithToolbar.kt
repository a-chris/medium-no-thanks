package net.achris.mediumnothanks.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.extensions.flipIn
import net.achris.mediumnothanks.extensions.flipOut
import net.achris.mediumnothanks.extensions.playAnimation
import net.achris.mediumnothanks.model.ColorMode
import net.achris.mediumnothanks.ui.activity.ThemeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class FragmentWithToolbar : Fragment() {

    protected val themeViewModel: ThemeViewModel by sharedViewModel()

    private val colorModeMenuItem by lazy {
        requireView().findViewById<View>(R.id.action_color_mode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
            To be applied after the color mode change
         */
        if (themeViewModel.currentColorModel == ColorMode.DARK) {
            colorModeMenuItem.flipIn()
        }
    }

    /*
        To be called during onCreateView()
     */
    protected fun setupToolbar(refreshAppOnThemeChange: Boolean) {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_color_mode -> {
                    toggleTheme(refreshAppOnThemeChange)
                    colorModeMenuItem.flipOut()
                }
                R.id.action_drawer -> openDrawer()
            }
            true
        }
    }

    private fun openDrawer() {
        activity?.drawer_layout?.openDrawer(GravityCompat.END)
    }

    private fun toggleTheme(refreshApp: Boolean) {
        themeViewModel.toggleColorMode()
        if (refreshApp) themeViewModel.applyAppTheme()
    }

}