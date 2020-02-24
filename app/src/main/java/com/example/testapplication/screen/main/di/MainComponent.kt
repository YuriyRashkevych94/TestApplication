package com.example.testapplication.screen.main.di

import com.example.testapplication.di.ActivityScope
import com.example.testapplication.screen.main.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}