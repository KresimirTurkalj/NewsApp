package com.example.newsapp.interfaces

import com.example.newsapp.model.ArticleData

interface ItemClickObserver {
    fun onItemClicked(arrayOfArticleData: Array<ArticleData>, position: Int)
}
