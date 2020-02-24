package com.example.testapplication.application

import android.app.Application
import com.example.testapplication.di.ApplicationComponent
import com.example.testapplication.di.ApplicationModule
import com.example.testapplication.di.DaggerApplicationComponent

class Application : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}