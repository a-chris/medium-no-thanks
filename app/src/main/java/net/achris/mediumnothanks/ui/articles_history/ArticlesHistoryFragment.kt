package net.achris.mediumnothanks.ui.articles_history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_articles_history.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.extensions.gone
import net.achris.mediumnothanks.extensions.visible
import net.achris.mediumnothanks.model.Article
import net.achris.mediumnothanks.ui.activity.ArticlesViewModel
import net.achris.mediumnothanks.util.GITHUB_PROJECT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class ArticlesHistoryFragment : Fragment(), ArticleClickListener {

    private val articlesViewModel: ArticlesViewModel by sharedViewModel()
    private val articlesAdapter by lazy { ArticlesAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_articles_history, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(history_list_articles) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
        }

        articlesViewModel.readArticlesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    history_list_articles.visible()
                    error_empty_list.gone()
                    articlesAdapter.submitList(it)
                } else {
                    history_list_articles.gone()
                    error_empty_list.visible()
                }
            })

        history_github.setOnClickListener {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_PROJECT))
            startActivity(openBrowserIntent)
        }
    }

    override val onArticleClick: (article: Article) -> Unit
        get() = { article -> articlesViewModel.readArticle(article) }
}