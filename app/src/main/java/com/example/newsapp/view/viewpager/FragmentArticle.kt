package com.example.newsapp.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.newsapp.R


class FragmentArticle : Fragment() {

    companion object {
        private const val KEY_WEBSITE_URL = "com.example.newsapplication.extra.WEBSITE_URL"
        fun newInstance(websiteLink: String): FragmentArticle {
            val args = Bundle()
            args.putString(KEY_WEBSITE_URL, websiteLink)
            return FragmentArticle().also { it.arguments = args }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_page_article, container, false)
        val webView = view.findViewById<WebView>(R.id.fragment_page_webview)
        webView.loadUrl(arguments!!.getString(KEY_WEBSITE_URL))
        webView.webViewClient = WebViewClient()
        return view
    }
}