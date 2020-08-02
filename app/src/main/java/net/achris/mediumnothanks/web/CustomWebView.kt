package net.achris.mediumnothanks.web

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.util.AttributeSet
import android.webkit.*
import net.achris.mediumnothanks.data.MEDIUM_BASE_PAGE_URL

class CustomWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : WebView(context, attrs, defStyle) {

    var onLoadingFinished: (() -> Unit)? = null
    var onLoadingStarted: (() -> Unit)? = null

    init {
        with(settings) {
            // Not sure if needed to have anonymous navigation
            cacheMode = WebSettings.LOAD_NO_CACHE
            setAppCacheEnabled(false)

            blockNetworkLoads = false
            javaScriptEnabled = true
            blockNetworkImage = false
            allowContentAccess = true
            loadsImagesAutomatically = true

            /*
                Night mode setup
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val nightMode =
                    context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                    forceDark = WebSettings.FORCE_DARK_ON
                }
            }
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
                    Avoids the redirect to the browser or the Medium app
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
        onLoadingStarted?.invoke()
        super.loadUrl(url)
    }
}