package com.example.weathertaskapp.di

import LocationHelper
import com.example.weathertaskapp.data.local.TaskDatabase
import com.example.weathertaskapp.data.local.TaskRepository
import com.example.weathertaskapp.data.remote.WeatherApi
import com.example.weathertaskapp.data.remote.WeatherRepository
import com.example.weathertaskapp.ui.task.TaskViewModel
import com.example.weathertaskapp.ui.weather.WeatherViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single<WeatherApi> {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    single { WeatherRepository(get()) }

    single { LocationHelper(get()) }

    single {
        val db = TaskDatabase.getDatabase(get())
        TaskRepository(db.taskDao())
    }

    viewModel { WeatherViewModel(get(), get()) }

    viewModel { TaskViewModel(get()) }
}
