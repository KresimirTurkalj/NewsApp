package com.example.newsapp.model

import java.io.Serializable

data class ArticleData(
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String
) : Serializable {

    companion object {
        fun newInstance(line: String): ArticleData {
            val argsList = line.split(delimiters = arrayOf(","), ignoreCase = true, limit = 4)
            val title = argsList[0]
            val description = argsList[1]
            val url: String = argsList[2]
            val urlToImage = argsList[3]
            return ArticleData(title, description, url, urlToImage)
        }
    }

    override fun toString(): String {
        return "$title,$description,$url,$urlToImage"
    }
}