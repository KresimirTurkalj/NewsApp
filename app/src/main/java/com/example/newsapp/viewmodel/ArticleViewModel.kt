package com.example.newsapp.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.model.ArticleData
import com.example.newsapp.view.ui.NewsActivity
import java.io.Serializable

class ArticleViewModel(intent: Intent) : ViewModel() {
    private var arrayOfArticleData: Array<ArticleData>
    var arrayOfWebsiteLinks: Array<String>
        private set
    var startPosition = 0
        private set

    init {
        val result = intent.getSerializableExtra(NewsActivity.EXTRA_ARTICLE_ARRAY) as Array<Serializable>
        arrayOfArticleData = result.mapNotNull { it as? ArticleData }.toTypedArray()
        startPosition = intent.getIntExtra(NewsActivity.EXTRA_ARTICLE_POSITION, 0)
        arrayOfWebsiteLinks = Array(arrayOfArticleData.size) { arrayOfArticleData[it].url }
    }
}

class ArticleViewModelFactory(val intent: Intent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Intent::class.java).newInstance(intent)
    }

}