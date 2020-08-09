package net.achris.mediumnothanks.ui.web

import android.content.Context
import android.util.AttributeSet
import android.webkit.*
import net.achris.mediumnothanks.util.MEDIUM_BASE_PAGE_URL

class CustomWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : WebView(context, attrs, defStyle) {

    var onLoadingFinished: (() -> Unit)? = null
    var onLoadingStarted: (() -> Unit)? = null

    init {
        with(settings) {
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            blockNetworkLoads = false
            javaScriptEnabled = true
            blockNetworkImage = false
            allowContentAccess = true
            loadsImagesAutomatically = true
        }
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                /*
                    Avoids redirecting to the browser or the Medium app
                 */
                loadUrl(url)
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                /*
                    Execute only while loading the real Medium article
                 */
                if (url?.startsWith(MEDIUM_BASE_PAGE_URL) == true) {
                    // Hides Medium navbar
                    view?.loadUrl("""javascript:document.getElementsByTagName("nav")[0].setAttribute("style","display:none;");""")
                }
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                onLoadingFinished?.invoke()
            }
        }

        CookieManager.getInstance().setAcceptCookie(false)
    }

    override fun loadUrl(url: String?) {
        if (url?.startsWith("javascript") == false) {
            /*
                Not triggered while executing javascript code
             */
            onLoadingStarted?.invoke()
        }
        super.loadUrl(url)
    }
}