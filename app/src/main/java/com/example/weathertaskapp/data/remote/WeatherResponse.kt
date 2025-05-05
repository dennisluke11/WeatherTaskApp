        data class WeatherResponse(
            val location: Location,
            val current: Current,
            val forecast: Forecast
        )

        data class Forecast(
            val forecastday: List<ForecastDay>
        )

        data class ForecastDay(
            val date: String,
            val day: Day,
            val astro: Astro
        )

        data class Day(
            val maxtemp_c: Double,
            val mintemp_c: Double,
            val avgtemp_c: Double,
            val condition: Condition
        )

        data class Astro(
            val sunrise: String,
            val sunset: String,
            val moonrise: String,
            val moonset: String
        )

        data class Location(
            val name: String,
            val region: String,
            val country: String,
            val lat: Double,
            val lon: Double,
            val tz_id: String,
            val localtime: String
        )

        data class Current(
            val temp_c: Double,
            val condition: Condition
        )

        data class Condition(
            val text: String,
            val icon: String
        )
