package com.example.testapplication.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* I skipped Repository step to keep app simple.
   Usually ViewModel talks to Repository and Repository talks to ApiProvider and/or DatabaseProvider

   Also, I do all the error handling logic here to keep it simple. Usually I create separate classes for it. */

class ApiProvider(private val apiService: ApiService,
                  private val networkHelper: NetworkConnectionHelper) {

    fun pullArticles(): MutableLiveData<Result<List<Article>>> {
        val liveData = MutableLiveData<Result<List<Article>>>()
        liveData.postValue(Loading())

        if(networkHelper.isOffline()) {
            liveData.postValue(Failure(networkHelper.getNoInternetError()))
            return liveData
        }

        val call = apiService.pullArticles()
        call.enqueue(object : Callback<Feed> {
            override fun onFailure(call: Call<Feed>, t: Throwable) {
                liveData.postValue(Failure(networkHelper.getServerError()))
            }

            override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                if(response.isSuccessful && response.body() != null) {
                    val children = response.body()?.data?.children

                    val articles = mutableListOf<Article>()
                    children?.forEach {
                        articles.add(it.data)
                    }

                    liveData.postValue(Success(articles))
                }
                else {
                    liveData.postValue(Failure(networkHelper.getServerError())) // simple error handling
                }
            }
        })

        return liveData
    }
}