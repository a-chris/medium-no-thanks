package net.achris.mediumnothanks.ui.web

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_webview.*
import kotlinx.android.synthetic.main.fragment_webview.view.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.model.ColorMode
import net.achris.mediumnothanks.ui.FragmentWithToolbar
import net.achris.mediumnothanks.ui.activity.ArticlesViewModel
import net.achris.mediumnothanks.util.MEDIUM_BASE_LINK
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

const val SHARED_URL = "shared_url"

class WebViewFragment : FragmentWithToolbar() {
    private val articlesViewModel: ArticlesViewModel by sharedViewModel()
    private var sharedUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sharedUrl = it.getString(SHARED_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(refreshAppOnThemeChange = false)
        themeViewModel.themeLiveData.observe(viewLifecycleOwner, Observer {
            applyColorMode(it.colorMode)
        })
        themeViewModel.currentColorModel?.let {
            applyColorMode(it)
        }

        articlesViewModel.currentArticleLiveData.observe(viewLifecycleOwner, Observer {
            webview.loadUrl(it.url)
            Toast.makeText(context, it.title, Toast.LENGTH_LONG).show()
        })
        with(view) {
            webview.onLoadingStarted = { progress_container.visibility = View.VISIBLE }
            webview.onLoadingFinished = { progress_container.visibility = View.GONE }
        }
        sharedUrl?.let { url ->
            articlesViewModel.rawUrlReceived(url)
        }
    }

    private fun applyColorMode(cm: ColorMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            webview.settings.forceDark =
                if (cm == ColorMode.DARK)
                    WebSettings.FORCE_DARK_ON
                else
                    WebSettings.FORCE_DARK_OFF
        }
        if (cm == ColorMode.DARK) {
            webview.setBackgroundColor(Color.BLACK)
        }
    }

    companion object {
        val TAG = WebViewFragment::class.java.name
        @JvmStatic
        fun newInstance() = WebViewFragment()

        @JvmStatic
        fun newInstance(sharedUrl: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(SHARED_URL, sharedUrl)
                }
            }
    }
}