package com.example.newsapp.view.ui

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.interfaces.ItemClickObserver
import com.example.newsapp.model.ArticleData
import com.example.newsapp.model.NewsData
import com.example.newsapp.service.NewsLoadService
import com.example.newsapp.view.recyclerview.NewsRecyclerViewAdapter
import com.example.newsapp.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity(), ItemClickObserver {

    companion object {
        const val EXTRA_ARTICLE_ARRAY = "com.example.newsapplication.extra.Array<ArticleData>"
        const val EXTRA_ARTICLE_POSITION = "com.example.newsapplication.extra.Int"
    }

    private val newsRecyclerView by lazy { initializeRecyclerView() }
    private lateinit var newsViewModel: NewsViewModel

    private val newsServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {}
        override fun onServiceConnected(name: ComponentName, service: IBinder) {}
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val newsData = intent.getSerializableExtra(NewsLoadService.EXTRA_ARTICLES) as NewsData?
            if (newsData != null) {
                newsViewModel.setNewsData(newsData)
            } else {
                showDialogForFailure()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeViewModel()
        bindToNewsDataLoadService()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(baseContext).registerReceiver(broadcastReceiver, IntentFilter(NewsLoadService.ACTION_SERVICE_LOADED))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(baseContext).unregisterReceiver(broadcastReceiver)
    }

    private fun initializeViewModel() {
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsViewModel.observe(this) { changeNewsRecyclerView(it) }
    }

    private fun initializeRecyclerView(): RecyclerView {
        setContentView(R.layout.activity_news)
        val newsRecyclerView: RecyclerView = findViewById(R.id.recycler_view_news)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        return newsRecyclerView
    }

    private fun bindToNewsDataLoadService() {
        bindService(Intent(this, NewsLoadService::class.java), newsServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun changeNewsRecyclerView(newsData: NewsData) {
        newsRecyclerView.adapter = NewsRecyclerViewAdapter(this, newsData.getArticles())
    }

    private fun showDialogForFailure() {
        AlertDialog.Builder(this)
                .setTitle(R.string.title_call_failed)
                .setMessage(R.string.text_call_failed)
                .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int -> }
                .create().show()
    }

    override fun onItemClicked(arrayOfArticleData: Array<ArticleData>, position: Int) {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(EXTRA_ARTICLE_ARRAY, arrayOfArticleData)
        intent.putExtra(EXTRA_ARTICLE_POSITION, position)
        startActivity(intent)
    }
}