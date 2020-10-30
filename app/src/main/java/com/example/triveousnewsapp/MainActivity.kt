package com.example.triveousnewsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        tabLayout.getTabAt(0)?.select()

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
            "Business" -> showingNewsOfTheTabSelected("business","c")
            "Entertainment" -> showingNewsOfTheTabSelected("entertainment","c")
            "Sports" -> showingNewsOfTheTabSelected("sports","c")
            "General" -> showingNewsOfTheTabSelected("general", "c")
            "Health" -> showingNewsOfTheTabSelected("health", "c")
            "Science" -> showingNewsOfTheTabSelected("science", "c")
            "Technology" -> showingNewsOfTheTabSelected("technology","c")
            "BBC" -> showingNewsOfTheTabSelected("bbc-news", "s")
            "The Times of India" -> showingNewsOfTheTabSelected("the-times-of-india", "s")
            "The Hindu" -> showingNewsOfTheTabSelected("the-hindu", "s")

        }
    }


    private fun showingNewsOfTheTabSelected(type: String, from: String){
        rvNews.visibility= View.GONE
        pbLoading.visibility= View.VISIBLE
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
            }else{
                Toast.makeText(this, "Else block", Toast.LENGTH_SHORT).show()
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