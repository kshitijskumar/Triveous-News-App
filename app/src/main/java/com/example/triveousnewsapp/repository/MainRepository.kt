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