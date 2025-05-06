package com.example.weathertaskapp

import android.app.Application
import com.example.weathertaskapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherTaskApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherTaskApp)
            modules(appModule)
        }
    }
}