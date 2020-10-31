package com.example.triveousnewsapp.repository

import android.util.Log
import com.example.triveousnewsapp.retrofit.Articles
import com.example.triveousnewsapp.retrofit.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val newsService: NewsService
) {
    private var newsResult= listOf<Articles>()

    fun showNewsResult()= newsResult

    /**Calls the api and fetches the data and sets newsResult.
     * @param category eg. Business, General, Health etc.
     */
    suspend fun getNewsFromCategoryApi(category: String){
        withContext(Dispatchers.IO){
            newsResult = try {
                val response = newsService.getNewsFromCategory(category)
                response.body()!!.newsArticles
            }catch (e: Exception){
                Log.d("MainRepo", "In getNewsFromApi ${e.message}")
                listOf()
            }
        }
    }

    /**Calls the api and fetches the data and sets newsResult.
     * @param source eg. The Times of India, The Hindu etc.
     */
    suspend fun getNewsFromSourceApi(source: String){
        withContext(Dispatchers.IO){
            newsResult = try {
                val response = newsService.getNewsFromSource(source)
                response.body()!!.newsArticles
            }catch (e: Exception){
                Log.d("MainRepo", "In getNewsFromApi ${e.message}")
                listOf()
            }
        }
    }
}