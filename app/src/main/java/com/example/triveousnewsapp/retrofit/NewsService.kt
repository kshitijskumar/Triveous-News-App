package com.example.triveousnewsapp.retrofit

import com.example.triveousnewsapp.retrofit.NewsService.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=in&pageSize=50&apiKey=$API_KEY")
    suspend fun getNewsFromCategory(@Query("category") category: String): Response<NewsItems>

    @GET("top-headlines?pageSize=50&apiKey=$API_KEY")
    suspend fun getNewsFromSource(@Query("sources") source: String): Response<NewsItems>

    companion object{
        private const val API_KEY = "f88b67ba0e3c49d8badfbfd45227c557"
    }
}