package com.example.testapplication.screen.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.di.ActivityScope
import com.example.testapplication.network.ApiProvider
import com.example.testapplication.screen.main.MainActivity
import com.example.testapplication.screen.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val activity: MainActivity) {

    @Provides
    @ActivityScope
    fun provideViewModel(apiProvider: ApiProvider): MainViewModel {
        return ViewModelProviders.of(activity,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return MainViewModel(apiProvider) as T
                }
            }).get(MainViewModel::class.java)
    }
}