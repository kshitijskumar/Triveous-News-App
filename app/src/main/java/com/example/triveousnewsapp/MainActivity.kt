package com.example.triveousnewsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triveousnewsapp.adapter.NewsAdapter
import com.example.triveousnewsapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, NewsAdapter.OnNewsClicked {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout.addOnTabSelectedListener(this)
        val tabPosition= viewModel.getCurrentTabPosition()
        tabLayout.getTabAt(tabPosition)?.select()

        rvNews.layoutManager= LinearLayoutManager(this)
        val adapter= NewsAdapter(listOf(), this)
        rvNews.adapter= adapter
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabSelected(tab?.text.toString())
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {
        tabSelected(tab?.text.toString())
    }

    private fun tabSelected(tabName: String){
        when (tabName) {
            "Business" -> {
                showingNewsOfTheTabSelected("business", "c")
                viewModel.setCurrentTabPosition(4)
            }
            "Entertainment" -> {
                showingNewsOfTheTabSelected("entertainment", "c")
                viewModel.setCurrentTabPosition(5)
            }
            "Sports" -> {
                showingNewsOfTheTabSelected("sports", "c")
                viewModel.setCurrentTabPosition(6)
            }
            "General" -> {
                showingNewsOfTheTabSelected("general", "c")
                viewModel.setCurrentTabPosition(3)
            }
            "Health" -> {
                showingNewsOfTheTabSelected("health", "c")
                viewModel.setCurrentTabPosition(7)
            }
            "Science" -> {
                showingNewsOfTheTabSelected("science", "c")
                viewModel.setCurrentTabPosition(8)
            }
            "Technology" -> {
                showingNewsOfTheTabSelected("technology", "c")
                viewModel.setCurrentTabPosition(9)
            }
            "BBC" -> {
                showingNewsOfTheTabSelected("bbc-news", "s")
                viewModel.setCurrentTabPosition(2)
            }
            "The Times of India" -> {
                showingNewsOfTheTabSelected("the-times-of-india", "s")
                viewModel.setCurrentTabPosition(0)
            }
            "The Hindu" -> {
                showingNewsOfTheTabSelected("the-hindu", "s")
                viewModel.setCurrentTabPosition(1)
            }

        }
    }


    private fun showingNewsOfTheTabSelected(type: String, from: String){
        rvNews.visibility= View.INVISIBLE
        pbLoading.visibility= View.VISIBLE
        tvNoNews.visibility= View.GONE
         if (from=="c") {
             viewModel.getCategoryNewsFromRepo(type)
         }else{
             viewModel.getSourceNewsFromRepo(type)
         }
        viewModel.newsLiveData.observe(this, {
            if (it.isNotEmpty()){
                val adapter= NewsAdapter(it, this)
                rvNews.swapAdapter(adapter, true)
                pbLoading.visibility= View.GONE
                rvNews.visibility= View.VISIBLE
                tvNoNews.visibility= View.GONE

            }else{
                pbLoading.visibility= View.GONE
                rvNews.visibility= View.INVISIBLE
                tvNoNews.visibility= View.VISIBLE
            }
        })
    }

    override fun newsItemClicked(articleUrl: String) {
        Intent(this, DetailedPage::class.java).also {
            it.putExtra("ArticleUrl", articleUrl)
            startActivity(it)
        }
    }
}