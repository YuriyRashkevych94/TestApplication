package com.example.testapplication.di

import android.app.Application
import com.example.testapplication.network.ApiProvider
import com.example.testapplication.network.ApiService
import com.example.testapplication.network.NetworkConnectionHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/* I have created only one Dagger-Module to keep app simple. */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() : Application {
        return application
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(Gson())
    }

    @Provides
    @Singleton
    fun provideRetrofit(coverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(" https://www.reddit.com/")
            .addConverterFactory(coverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionHelper(): NetworkConnectionHelper {
        return NetworkConnectionHelper(application)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiProvider(apiService: ApiService, networkConnectionHelper: NetworkConnectionHelper): ApiProvider {
        return ApiProvider(apiService, networkConnectionHelper)
    }
}