package dev.capstone.satako_mobile.presentation.articles

import dev.capstone.satako_mobile.data.model.dummy.Article

interface OnArticleClickListener {
    fun onArticleClick(article: Article)
}