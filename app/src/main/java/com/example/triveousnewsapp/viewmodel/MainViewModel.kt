package com.example.triveousnewsapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triveousnewsapp.repository.MainRepository
import com.example.triveousnewsapp.retrofit.Articles
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository
): ViewModel() {

    val newsLiveData= MutableLiveData<List<Articles>>()

    fun getCategoryNewsFromRepo(category: String)= viewModelScope.launch {
        repository.getNewsFromCategoryApi(category)
        newsLiveData.postValue(repository.newsResult)
    }

    fun getSourceNewsFromRepo(source: String)= viewModelScope.launch {
        repository.getNewsFromSourceApi(source)
        newsLiveData.postValue(repository.newsResult)
    }

}