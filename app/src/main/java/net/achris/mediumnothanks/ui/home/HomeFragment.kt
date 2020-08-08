package net.achris.mediumnothanks.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.ui.FragmentWithToolbar


class HomeFragment : FragmentWithToolbar() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(refreshAppOnThemeChange = true)
        home_github.setOnClickListener { openGitHub() }
    }

}