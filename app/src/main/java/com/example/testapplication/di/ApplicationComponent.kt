package com.example.testapplication.di

import com.example.testapplication.screen.main.di.MainComponent
import com.example.testapplication.screen.main.di.MainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun plusMainComponent(module: MainModule): MainComponent
}