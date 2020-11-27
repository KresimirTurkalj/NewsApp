package com.example.newsapp.view.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import com.example.newsapp.view.viewpager.FragmentStateAdapter
import com.example.newsapp.viewmodel.ArticleViewModel
import com.example.newsapp.viewmodel.ArticleViewModelFactory


class ArticleActivity : FragmentActivity() {

    private lateinit var viewPagerArticle: ViewPager2
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_display)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        articleViewModel = ViewModelProvider(this, ArticleViewModelFactory(intent)).get(ArticleViewModel::class.java)
        initializeViewPagerAdapter()
    }

    private fun initializeViewPagerAdapter() {
        viewPagerArticle = findViewById(R.id.view_pager_article)
        viewPagerArticle.adapter = FragmentStateAdapter(this, articleViewModel.arrayOfWebsiteLinks)
        viewPagerArticle.currentItem = articleViewModel.startPosition
    }
}
