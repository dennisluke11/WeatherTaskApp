package com.example.weathertaskapp.ui.weather


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weathertaskapp.R


@Composable
fun WeatherCardContent(viewModel: WeatherViewModel) {
    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = state.city) {
        if (state.city == null && state.weather == null && !state.isLoading) {
            viewModel.fetchWeather("Pretoria")
        }
    }

    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.errorMessage != null -> {
                Text("Error: ${state.errorMessage}")
            }

            else -> {
                state.weather?.let { weather ->
                    weather.current.temp_c?.toFloat()?.let { temp ->
                        WeatherCard(
                            temperature = temp,
                            condition = weather.current.condition.text,
                            sunrise = weather.forecast.forecastday.getOrNull(0)?.astro?.sunrise
                                ?: "N/A",
                            sunset = weather.forecast.forecastday.getOrNull(0)?.astro?.sunset
                                ?: "N/A",
                            city = weather.location.name,
                        )
                    } ?: Text(stringResource(R.string.temperature_data_unavailable))
                } ?: Text(stringResource(R.string.no_weather_data))
            }
        }
    }
}


