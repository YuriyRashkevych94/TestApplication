package com.example.testapplication.network

import com.example.testapplication.data.Feed
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("r/kotlin/.json")
    fun pullArticles(): Call<Feed>
}