package com.example.newsapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.interfaces.ItemClickObserver
import com.example.newsapp.model.ArticleData
import com.squareup.picasso.Picasso

class NewsRecyclerViewAdapter(private val parentActivity: ItemClickObserver, private val arrayOfArticleData: Array<ArticleData>) : RecyclerView.Adapter<NewsRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_article, parent, false)
        return NewsRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayOfArticleData.count()
    }

    override fun onBindViewHolder(holder: NewsRecyclerViewHolder, position: Int) {
        holder.articleTextView.text = arrayOfArticleData[position].description
        Picasso.get().load(arrayOfArticleData[position].urlToImage).into(holder.articleImageView)
        holder.itemView.setOnClickListener { parentActivity.onItemClicked(arrayOfArticleData, position) }
    }
}