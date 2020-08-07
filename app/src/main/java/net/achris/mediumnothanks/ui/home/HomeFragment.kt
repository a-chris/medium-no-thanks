package net.achris.mediumnothanks.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.toolbar.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.activity.ActivityViewModel
import net.achris.mediumnothanks.data.GITHUB_PROJECT


class HomeFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity())[ActivityViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            home_github.setOnClickListener { openGitHub() }
            toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_color_mode -> viewModel.toggleColorMode()
                    R.id.action_github -> openGitHub()
                }
                true
            }
        }
    }

    private fun openGitHub() {
        val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_PROJECT))
        startActivity(openBrowserIntent)
    }

}