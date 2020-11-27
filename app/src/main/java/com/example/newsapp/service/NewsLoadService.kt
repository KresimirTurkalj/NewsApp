package com.example.newsapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.newsapp.model.NewsData
import com.example.newsapp.service.file.FileService
import com.example.newsapp.service.retrofit.NewsService.Companion.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsLoadService : Service() {

    companion object {
        const val ACTION_SERVICE_LOADED = "com.example.newsapplication.service.NewsLoadService.action.SERVICE_LOADED"
        const val EXTRA_ARTICLES = "com.example.newsapplication.service.NewsLoadService.extra.EXTRA_ARTICLES"
        private const val apiKey = "6946d0c07a1c4555a4186bfcade76398"
        private const val sortBy = "top"
        private const val source = "bbc-news"
        private const val fileName = "news_response"
    }

    class NewsServiceBinder : Binder()

    private val newsServiceBinder = NewsServiceBinder()
    private val newsApiCall = instance.getNewsArticles(source, sortBy, apiKey)
    private val fileStorage by lazy { FileService.getInstance(applicationContext.filesDir, fileName) }
    private val newsCallback = object : Callback<NewsData> {

        override fun onFailure(call: Call<NewsData?>?, t: Throwable) {
            sendNewsResponseBroadcast(null)
        }

        override fun onResponse(call: Call<NewsData?>?, responseData: Response<NewsData?>?) {
            sendNewsResponseBroadcast(responseData?.body())
            if (responseData != null) {
                val it = responseData.body()
                if (it != null) {
                    fileStorage.saveNews(it)
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        if (fileStorage.isNewsResponseUpdated()) {
            sendNewsResponseBroadcast(fileStorage.loadNews())
        } else {
            callNewsApi()
        }
        return newsServiceBinder
    }

    private fun callNewsApi() {
        newsApiCall.enqueue(newsCallback)
    }

    fun sendNewsResponseBroadcast(body: NewsData?) {
        val broadcastIntent = Intent(ACTION_SERVICE_LOADED)
        broadcastIntent.putExtra(EXTRA_ARTICLES, body)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        stopSelf()
    }
}