    package com.example.weathertaskapp.utils

    import java.text.SimpleDateFormat
    import java.util.Calendar
    import java.util.Locale

    object ThemeToggleHelper {

        fun shouldUseDarkTheme(sunrise: String, sunset: String, localtime: String): Boolean {
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

            return try {
                val sunriseTime = timeFormat.parse(sunrise)
                val sunsetTime = timeFormat.parse(sunset)
                val localDateTime = dateTimeFormat.parse(localtime)

                val calendar = Calendar.getInstance().apply {
                    time = localDateTime!!
                }

                val localTimeOnly = Calendar.getInstance().apply {
                    time = sunriseTime!!
                    set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY))
                    set(Calendar.MINUTE, calendar.get(Calendar.MINUTE))
                }

                localTimeOnly.time.before(sunriseTime) || localTimeOnly.time.after(sunsetTime)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

