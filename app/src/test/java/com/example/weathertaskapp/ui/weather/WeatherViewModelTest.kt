package com.example.weathertaskapp.ui.weather

import Astro
import Condition
import Current
import Day
import Forecast
import ForecastDay
import Location
import LocationHelper
import WeatherResponse
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weathertaskapp.data.remote.WeatherRepository
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: WeatherRepository

    @Mock
    private lateinit var locationHelper: LocationHelper

    private lateinit var viewModel: WeatherViewModel

    private val mockWeatherResponse = WeatherResponse(
        location = Location(
            name = "Cape Town",
            region = "Western Cape",
            country = "South Africa",
            lat = -33.9258,
            lon = 18.4232,
            tz_id = "Africa/Johannesburg",
            localtime = "2025-05-05 14:00"
        ),
        current = Current(
            temp_c = 24.5,
            condition = Condition(
                text = "Partly Cloudy",
                icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
            )
        ),
        forecast = Forecast(
            forecastday = listOf(
                ForecastDay(
                    date = "2025-05-05",
                    day = Day(
                        maxtemp_c = 26.0,
                        mintemp_c = 16.0,
                        avgtemp_c = 21.0,
                        condition = Condition(
                            text = "Sunny",
                            icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                        )
                    ),
                    astro = Astro(
                        sunrise = "06:45 AM",
                        sunset = "06:00 PM",
                        moonrise = "07:00 PM",
                        moonset = "06:00 AM"
                    )
                )
            )
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository, locationHelper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test fetchWeather updates state with success`() = runTest {
        val city = "Cape Town"
        `when`(repository.getForecastWeather(eq(city), any())).thenReturn(mockWeatherResponse)

        viewModel.fetchWeather(city)
        advanceUntilIdle()

        val state = viewModel.uiState.first { !it.isLoading }
        assertEquals(city, state.city)
        assertEquals(mockWeatherResponse, state.weather)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `test loadCityFromLocation updates city and fetches weather`() = runTest {

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        val city = "Johannesburg"
        val weatherResponse = WeatherResponse(
            location = Location(
                name = city,
                region = "Gauteng",
                country = "South Africa",
                lat = -26.2041,
                lon = 28.0473,
                tz_id = "Africa/Johannesburg",
                localtime = "2024-05-05 12:00"
            ),
            current = Current(
                temp_c = 22.0,
                condition = Condition(
                    text = "Cloudy",
                    icon = "//cdn.weatherapi.com/weather/64x64/day/119.png"
                )
            ),
            forecast = Forecast(forecastday = emptyList())
        )

        `when`(locationHelper.getCurrentCity()).thenReturn(city)
        `when`(repository.getForecastWeather(eq(city), any())).thenReturn(weatherResponse)

        viewModel.loadCityFromLocation()
        advanceUntilIdle()

        val state = viewModel.uiState.first { !it.isLoading }
        assertEquals(city, state.city)
        assertEquals(weatherResponse, state.weather)
        verify { Log.e(any(), any()) }
    }

    @Test
    fun `test loadCityFromLocation handles LocationPermissionDeniedException`() = runTest {
        val errorMessage = "Permission denied"
        `when`(locationHelper.getCurrentCity()).thenThrow(RuntimeException(errorMessage))

        viewModel.loadCityFromLocation()
        advanceUntilIdle()

        val state = viewModel.uiState.first { !it.isLoading }
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `test loadCityFromLocation handles LocationUnavailableException`() = runTest {
        val errorMessage = "Location unavailable"
        `when`(locationHelper.getCurrentCity()).thenThrow(RuntimeException(errorMessage))
        viewModel.loadCityFromLocation()
        advanceUntilIdle()
        val state = viewModel.uiState.first { !it.isLoading }
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `test loadCityFromLocation handles GeocodingFailedException`() = runTest {
        val errorMessage = "Geocoding failed"
        `when`(locationHelper.getCurrentCity()).thenThrow(RuntimeException(errorMessage))
        viewModel.loadCityFromLocation()
        advanceUntilIdle()
        val state = viewModel.uiState.first { !it.isLoading }
        assertEquals(errorMessage, state.errorMessage)
    }
}
