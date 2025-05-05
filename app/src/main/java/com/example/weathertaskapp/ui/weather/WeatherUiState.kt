package com.example.weathertaskapp.ui.weather

import WeatherResponse

data class WeatherUiState(
    val city: String? = null,
    val weather: WeatherResponse? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false
)