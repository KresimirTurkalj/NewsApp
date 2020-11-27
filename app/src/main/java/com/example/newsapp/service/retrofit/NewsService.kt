package com.example.newsapp.service.retrofit

import com.example.newsapp.model.NewsData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v1/articles")
    fun getNewsArticles(
            @Query("source") source: String,
            @Query("sortBy") sortBy: String,
            @Query("apiKey") apiKey: String
    ): Call<NewsData>

    companion object {
        val instance: NewsService by lazy {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            retrofit.create(NewsService::class.java)
        }
    }
}