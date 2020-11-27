package com.example.newsapp.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.view.viewpager.FragmentArticle.Companion.newInstance

class FragmentStateAdapter(
        holderFragmentActivity: FragmentActivity,
        private val arrayOfWebsiteLinks: Array<String>
) :
        FragmentStateAdapter(holderFragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return newInstance(arrayOfWebsiteLinks[position])
    }

    override fun getItemCount(): Int {
        return arrayOfWebsiteLinks.size
    }
}