package com.example.newsapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.newsapp.interfaces.NewsObservable
import com.example.newsapp.model.NewsData

class NewsViewModel : ViewModel(), NewsObservable {
    private var newsData = MutableLiveData<NewsData>()

    fun setNewsData(nD: NewsData?) {
        newsData.value = nD
    }

    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<NewsData>) {
        newsData.observe(lifecycleOwner, observer)
    }
}
