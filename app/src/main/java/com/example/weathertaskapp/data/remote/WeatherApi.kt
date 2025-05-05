    package com.example.weathertaskapp.data.remote

    import WeatherResponse
    import retrofit2.http.GET
    import retrofit2.http.Query

    interface WeatherApi {
        @GET("current.json")
        suspend fun getWeather(
            @Query("q") city: String,
            @Query("key") apiKey: String,
        ): WeatherResponse

        @GET("forecast.json")
        suspend fun getForecastWeather(
            @Query("q") location: String,
            @Query("key") apiKey: String,
            @Query("days") days: Int = 1
        ): WeatherResponse
    }