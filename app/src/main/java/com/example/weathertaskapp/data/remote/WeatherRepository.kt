package com.example.weathertaskapp.data.remote

import WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val api: WeatherApi) {

    suspend fun getForecastWeather(city: String, apiKey: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            api.getForecastWeather(city, apiKey, days = 1)
        }

}