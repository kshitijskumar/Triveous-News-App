package com.example.triveousnewsapp.di

import com.example.triveousnewsapp.retrofit.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NewsServiceModule {

    @Provides
    @Singleton
    fun providesRetrofit()= Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesNewsService(retrofit: Retrofit)= retrofit
        .create(NewsService::class.java)
}