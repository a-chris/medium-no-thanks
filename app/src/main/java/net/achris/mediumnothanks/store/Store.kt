package net.achris.mediumnothanks.store

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import net.achris.mediumnothanks.model.Article
import net.achris.mediumnothanks.model.ColorMode


private const val THEME_KEY = "theme"

private val articleListType = Types.newParameterizedType(List::class.java, Article::class.java)

class Store(context: Context, private val moshi: Moshi) {

    private val themePreferences = context.getSharedPreferences("theme.prefs", Context.MODE_PRIVATE)
    private val articlePreferences =
        context.getSharedPreferences("article.prefs", Context.MODE_PRIVATE)

    fun saveColorMode(cm: ColorMode) {
        themePreferences.edit().putString(THEME_KEY, cm.toString()).apply()
    }

    fun loadColorMode(): ColorMode {
        val storedValue = themePreferences.getString(THEME_KEY, ColorMode.LIGHT.toString())!!
        return ColorMode.fromString(storedValue)
    }

    fun saveReadArticles(articles: List<Article>) {
        articlePreferences.edit()
            .putString(
                "readArticles", moshi.adapter<List<Article>>(articleListType).toJson(articles)
            ).apply()
    }

    fun loadReadArticles(): List<Article> =
        articlePreferences.getString("readArticles", null)?.let {
            moshi.adapter<List<Article>>(articleListType).fromJson(it)
        } ?: listOf()
}