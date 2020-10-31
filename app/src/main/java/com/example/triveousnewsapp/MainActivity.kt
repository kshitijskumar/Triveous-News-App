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
        /**Gets the current tab position from the viewmodel
         * and sets the tab at that position again.
         */
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

    /**Distinguishes the tab by their name and calls the function to show the news corresponding to that
     * tab in the recycler view.
     * @param tabName The name/text of the tab
     */
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

    /**Calls the function to get the news and observes the news result from the viewmodel and displays the news in the
     * recycler view.
     * @param type Category or Source of the news eg. The Times of India, Business etc.
     * @param from Whether the news is from category(c) or from source(s)
     */
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

    /**Callback function to notify the activity that an item is clicked in the recycler view
     * to perform the corresponding task ie. starting a new activity to show complete news in a
     * webview.
     * @param articleUrl The url of the article tapped.
     */
    override fun newsItemClicked(articleUrl: String) {
        Intent(this, DetailedPage::class.java).also {
            it.putExtra("ArticleUrl", articleUrl)
            Toast.makeText(this, "Opening news in webview", Toast.LENGTH_SHORT).show()
            startActivity(it)
        }
    }
}