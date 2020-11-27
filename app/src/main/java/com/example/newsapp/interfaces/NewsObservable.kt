package com.example.newsapp.interfaces

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.newsapp.model.NewsData

interface NewsObservable {
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<NewsData>)
}
