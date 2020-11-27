package com.example.newsapp.service.file

import com.example.newsapp.model.ArticleData
import com.example.newsapp.model.NewsData
import java.io.File
import java.util.*

class FileService private constructor(private val file: File) {

    companion object {
        private const val millisToMinutes = 60000
        private const val endLine = "\n"
        private var instance: FileService? = null
        fun getInstance(fileDir: File, fileName: String): FileService {
            return instance ?: FileService(File(fileDir, fileName)).also { instance = it }
        }
    }

    private lateinit var fileText: MutableList<String>

    fun isNewsResponseUpdated(): Boolean {
        return if (!file.canRead()) {
            false
        } else {
            fileText = file.readLines().toMutableList()
            isFreshNews(fileText.popFirst().toLong())
        }
    }

    private fun isFreshNews(time: Long): Boolean {
        return Date().time - time < 5 * millisToMinutes
    }

    fun loadNews(): NewsData {
        val mutableList = mutableListOf<ArticleData>()
        for (line in fileText) {
            mutableList.add(ArticleData.newInstance(line))
        }
        return NewsData(mutableList.toTypedArray())
    }

    fun saveNews(newsData: NewsData) {
        if (!file.exists()) file.createNewFile()
        val articles = newsData.getArticles()
        file.writeText(Date().time.toString() + endLine)
        for (article in articles) run {
            file.appendText(article.toString() + endLine)
        }
    }

    fun <E> MutableList<E>.popFirst(): E {
        return this.first().also { this.remove(it) }
    }
}

