package dev.capstone.satako_mobile.presentation.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.model.dummy.Article
import dev.capstone.satako_mobile.databinding.ItemArticleBinding

class ArticlesAdapter(
    private val articles: List<Article>,
    private val onArticleClickListener: OnArticleClickListener
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    class ArticleViewHolder(
        private val binding: ItemArticleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, onArticleClickListener: OnArticleClickListener) {
            with(binding) {
                articleTitle.text = article.title
                articleAuthor.text = article.author
                articleDescription.text = article.description
                articleImageView.setImageResource(article.imageResourceId)
                articleCard.setOnClickListener {
                    onArticleClickListener.onArticleClick(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articles[position]
        holder.bind(item, onArticleClickListener)
    }
}