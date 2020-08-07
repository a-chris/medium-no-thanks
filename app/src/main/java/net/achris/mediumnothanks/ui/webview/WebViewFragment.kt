package net.achris.mediumnothanks.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_webview.view.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.data.MEDIUM_BASE_LINK

const val SHARED_URL = "shared_url"

class WebViewFragment : Fragment() {

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

        with(view) {
            webview.onLoadingStarted = { progress_container.visibility = View.VISIBLE }
            webview.onLoadingFinished = { progress_container.visibility = View.GONE }
            sharedUrl?.let { url ->
                webview.loadUrl(getMediumLink(url))
                getMediumTitle(url)?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun getMediumLink(rawLink: String): String {
        return if (rawLink.contains(MEDIUM_BASE_LINK)) {
            MEDIUM_BASE_LINK + rawLink.substringAfterLast(MEDIUM_BASE_LINK)
        } else {
            rawLink
        }
    }

    private fun getMediumTitle(rawLink: String): String? {
        return if (rawLink.contains(MEDIUM_BASE_LINK)) {
            rawLink.substringBefore(MEDIUM_BASE_LINK).trimEnd()
        } else {
            null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(sharedUrl: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(SHARED_URL, sharedUrl)
                }
            }
    }
}