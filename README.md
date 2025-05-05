                                           WeatherTaskApp

WeatherTaskApp is an Android application that provides weather information based on the user's location. It fetches weather data from the OpenWeather API and displays it on a user-friendly interface. The app includes a feature to toggle between light and dark themes based on the current weather conditions and offers overlays for better interaction with weather data.

Features
* Weather Information: Displays current weather, temperature, and conditions for the city.
* Location-Based Weather: Fetches weather data based on the user's current location (with location permission).
* Light/Dark Theme: Automatically switches between light and dark themes based on sunrise and sunset times.
* Weather Overlay: Allows users to view weather details in an overlay dialog.
* Location Permissions: Requests location permissions from the user and updates weather information accordingly.
* Weather Dialog: Displays weather information in a popup dialog with temperature, condition, sunrise, and sunset times.
Libraries & Dependencies
The app uses the following libraries:
* Koin: Dependency injection.
* Compose UI: For building the user interface.
* OpenWeather API: For fetching weather data.
* Kotlin Coroutines: For asynchronous operations.
* Permissions API: For location permissions.
* Material3: For Material Design 3 components.

Key Components
* WeatherViewModel: The ViewModel for managing weather data and theme updates.
* WeatherUiState: A data class representing the current state of weather information and UI.
* WeatherCard: A composable function displaying weather data (temperature, condition, sunrise/sunset).
* MainActivity: The main activity where the UI components are composed, and location permissions are handled.

Setup & Installation

1. Clone the repository: git clone [https://github.com/your-repository/weather-task-app.git](https://github.com/dennisluke11/WeatherTaskApp.git)
2. cd weather-task-app   
3. Add your OpenWeather API key:
    * In build.gradle, define your OpenWeather API key under BuildConfig.OPEN_WEATHER_API_KEY.
5. gradle CopyEdit   buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"YOUR_API_KEY\"")   
7. Install the dependencies:
    * Open the project in Android Studio and wait for Gradle to sync.
8. Run the app:
    * After syncing the dependencies, click on the "Run" button in Android Studio to install and launch the app on an emulator or a physical device.
Usage
1. Permissions:
    * The app will request location permissions upon the first launch. If granted, it will fetch weather data for the current location.
2. Weather Overlay:
    * Users can click on the weather icon in the app’s main screen to show or hide the weather overlay. The overlay shows weather information like temperature, condition, sunrise, and sunset times.
3. Dark Theme:
    * The app automatically switches to dark mode based on the sunrise and sunset times of the fetched weather location.
4. Error Handling:
    * If the app fails to fetch weather data or if location permissions are denied, appropriate error messages will be displayed.
