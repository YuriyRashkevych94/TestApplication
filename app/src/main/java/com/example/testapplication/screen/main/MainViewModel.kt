package com.example.testapplication.screen.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.*
import com.example.testapplication.network.ApiProvider

class MainViewModel(private val apiProvider: ApiProvider) : ViewModel() {

    val loading by lazy { MediatorLiveData<Boolean>() } // possibility to control loading
    val error by lazy { MediatorLiveData<Error>() }
    val articlesLiveData by lazy { MediatorLiveData<List<Article>>() }

    fun pullArticles() {
        val liveData = apiProvider.pullArticles()

        error.addSource(liveData) { result ->
            if(result is Failure) {
                error.postValue(result.error)
            }
        }

        articlesLiveData.addSource(liveData) { result ->
            if(result is Success) {
                articlesLiveData.postValue(result.data)
            }
        }
    }
}