package net.achris.mediumnothanks.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.achris.mediumnothanks.model.Article
import net.achris.mediumnothanks.store.Store
import net.achris.mediumnothanks.ui.Router
import net.achris.mediumnothanks.util.MEDIUM_BASE_LINK
import java.util.*

class ArticlesViewModel(private val store: Store, private val router: Router) : ViewModel() {

    val readArticlesLiveData: LiveData<List<Article>>
        get() = _readArticlesLiveData
    private val _readArticlesLiveData = MutableLiveData<List<Article>>()

    val currentArticleLiveData: LiveData<Article>
        get() = _currentArticleLiveData
    private val _currentArticleLiveData = MutableLiveData<Article>()

    init {
        _readArticlesLiveData.value = store.loadReadArticles()
    }

    fun rawUrlReceived(rawUrl: String) {
        val title = rawUrl.substringBefore(MEDIUM_BASE_LINK).trimEnd()
        val url = MEDIUM_BASE_LINK + rawUrl.substringAfterLast(MEDIUM_BASE_LINK)
        readArticle(Article(title, Date(), url))
    }

    fun readArticle(article: Article) {
        val updatedArticle = article.copy(date = Date())
        _currentArticleLiveData.value = updatedArticle
        /*
            If this article has been already read then move it on top of the history
         */
        _readArticlesLiveData.value = _readArticlesLiveData.value?.toMutableList()?.apply {
            val sameArticleAlreadyStoredIndex = indexOfFirst { it.title == updatedArticle.title }
            if (sameArticleAlreadyStoredIndex != -1) {
                removeAt(sameArticleAlreadyStoredIndex)
            }
            add(0, updatedArticle)
        } ?: listOf(article)
        store.saveReadArticles(_readArticlesLiveData.value!!)
        router.showArticle()
    }

}