package com.example.newsapp.model

import java.io.Serializable

data class NewsData(private val articles: Array<ArticleData>) : Serializable {

    fun getArticles(): Array<ArticleData> {
        return articles
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsData

        if (!articles.contentEquals(other.articles)) return false

        return true
    }

    override fun hashCode(): Int {
        return articles.contentHashCode()
    }

}