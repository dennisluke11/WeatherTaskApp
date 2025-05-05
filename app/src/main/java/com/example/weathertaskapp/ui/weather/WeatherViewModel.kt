package com.example.weathertaskapp.ui.weather

import GeocodingFailedException
import LocationHelper
import LocationPermissionDeniedException
import LocationUnavailableException
import WeatherResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertaskapp.data.remote.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.weathertaskapp.BuildConfig
import com.example.weathertaskapp.utils.ThemeToggleHelper

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun onOverlayToggled(isVisible: Boolean) {
        if (isVisible) {
            loadCityFromLocation()
        }
    }

    fun fetchWeather(city: String, apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = repository.getForecastWeather(city, apiKey)
                _uiState.value = _uiState.value.copy(
                    weather = response,
                    city = city,
                    isLoading = false,
                    errorMessage = null
                )
                updateThemeBasedOnWeather(response)
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun loadCityFromLocation() {
        viewModelScope.launch {
            try {
                val city = locationHelper.getCurrentCity()
                _uiState.value = _uiState.value.copy(city = city)
                fetchWeather(city)
            } catch (e: LocationPermissionDeniedException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (e: LocationUnavailableException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (e: GeocodingFailedException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    private fun updateThemeBasedOnWeather(weatherResponse: WeatherResponse) {
        val astro = weatherResponse.forecast.forecastday.firstOrNull()?.astro
        val localTime = weatherResponse.location.localtime

        if (astro != null) {
            val isDark = ThemeToggleHelper.shouldUseDarkTheme(astro.sunrise, astro.sunset, localTime)
            _uiState.value = _uiState.value.copy(isDarkTheme = isDark)
        } else {
            Log.e("WeatherViewModel", "Astro data is missing, cannot determine dark theme")
        }
    }
}
