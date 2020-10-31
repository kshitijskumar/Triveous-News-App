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
    private var tabPosition= 0

    fun setCurrentTabPosition(position: Int){
        tabPosition=position
    }

    fun getCurrentTabPosition()= tabPosition

    /**Calls the repository to fetch news data from the api.
     * The data from repository is then posted in newsLiveData.
     * @param category Category like Business, General etc.
     */
    fun getCategoryNewsFromRepo(category: String)= viewModelScope.launch {
        repository.getNewsFromCategoryApi(category)
        newsLiveData.postValue(repository.showNewsResult())
    }
    /**Calls the repository to fetch news data from the api.
     * The data from repository is then posted in newsLiveData.
     * @param source Source like The Times of India, The Hindu etc.
     */
    fun getSourceNewsFromRepo(source: String)= viewModelScope.launch {
        repository.getNewsFromSourceApi(source)
        newsLiveData.postValue(repository.showNewsResult())
    }

}