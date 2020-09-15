package net.achris.mediumnothanks.ui.articles_history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder_article.view.*
import net.achris.mediumnothanks.R
import net.achris.mediumnothanks.extensions.formatToShortDate
import net.achris.mediumnothanks.model.Article

interface ArticleClickListener {
    val onArticleClick: (Article) -> Unit
}


val diffUtil = object : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Article, newItem: Article) =
        oldItem == newItem
}

class ArticlesAdapter(private val listener: ArticleClickListener) :
    ListAdapter<Article, ArticleVH>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_article, parent, false)
        return ArticleVH(view, listener)
    }

    override fun onBindViewHolder(viewHolder: ArticleVH, position: Int) {
        viewHolder.initData(getItem(position))
    }

}


class ArticleVH(private val view: View, private val listener: ArticleClickListener) :
    RecyclerView.ViewHolder(view) {

    fun initData(article: Article) {
        with(view) {
            article_title.text = article.title
            article_date.text = article.date.formatToShortDate()
            article_container.setOnClickListener {
                listener.onArticleClick(article)
            }
        }
    }
}