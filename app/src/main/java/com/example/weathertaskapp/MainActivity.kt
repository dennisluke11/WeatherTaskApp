package com.example.weathertaskapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.weathertaskapp.ui.task.TaskScreen
import com.example.weathertaskapp.ui.task.TaskViewModel
import com.example.weathertaskapp.ui.theme.WeatherTaskAppTheme
import com.example.weathertaskapp.ui.weather.WeatherCardContent
import com.example.weathertaskapp.ui.weather.WeatherViewModel
import com.example.weathertaskapp.ui.weather.componets.WeatherDialogContent
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            val weatherViewModel: WeatherViewModel = getViewModel()
            val weatherUiState by weatherViewModel.uiState.collectAsState()
            val isDarkTheme = weatherUiState.isDarkTheme

            WeatherTaskAppTheme(darkTheme = isDarkTheme) {
                val taskViewModel: TaskViewModel = getViewModel()
                var showWeatherOverlay by remember { mutableStateOf(false) }

                val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
                val context = this@MainActivity
                val permissionGranted = remember { mutableStateOf(false) }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    permissionGranted.value = isGranted
                    if (isGranted) {
                        showWeatherOverlay = true
                        weatherViewModel.onOverlayToggled(true)
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            TaskScreen(viewModel = taskViewModel)
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 40.dp, start = 16.dp, end = 16.dp)
                        ) {
                            IconButton(onClick = {
                                val permissionCheck = ContextCompat.checkSelfPermission(
                                    context,
                                    locationPermission
                                )
                                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                    showWeatherOverlay = true
                                    weatherViewModel.onOverlayToggled(true)
                                } else {
                                    permissionLauncher.launch(locationPermission)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.WbTwilight,
                                    contentDescription = stringResource(R.string.weather_icon_description)
                                )
                            }
                            Text(
                                text = stringResource(R.string.weather_button),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        if (showWeatherOverlay) {
                            Dialog(onDismissRequest = { showWeatherOverlay = false }) {
                                Surface(
                                    shape = MaterialTheme.shapes.medium,
                                    tonalElevation = 8.dp,
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .wrapContentHeight()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .wrapContentSize()
                                    ) {
                                        Column(modifier = Modifier.wrapContentSize()) {
                                            Box(
                                                modifier = Modifier.fillMaxWidth(),
                                                contentAlignment = Alignment.TopEnd
                                            ) {
                                                IconButton(onClick = { showWeatherOverlay = false }) {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = stringResource(R.string.close_button)
                                                    )
                                                }
                                            }

                                            when {
                                                weatherUiState.isLoading -> {
                                                    WeatherDialogContent(
                                                        content = {
                                                            Text(stringResource(R.string.loading_weather_data))
                                                        },
                                                        onClose = { showWeatherOverlay = false }
                                                    )
                                                }

                                                weatherUiState.weather != null -> {
                                                    WeatherDialogContent(
                                                        content = {
                                                            WeatherCardContent(viewModel = weatherViewModel)
                                                        },
                                                        onClose = { showWeatherOverlay = false }
                                                    )
                                                }

                                                weatherUiState.errorMessage != null -> {
                                                    WeatherDialogContent(
                                                        content = {
                                                            Text(text = weatherUiState.errorMessage!!)
                                                        },
                                                        onClose = { showWeatherOverlay = false }
                                                    )
                                                }

                                                else -> {
                                                    WeatherDialogContent(
                                                        content = {
                                                            Text(stringResource(R.string.location_access_required))
                                                            Spacer(modifier = Modifier.height(8.dp))
                                                            Text(stringResource(R.string.location_permission_message))
                                                        },
                                                        onClose = { showWeatherOverlay = false }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
            }
            window.setBackgroundDrawableResource(android.R.color.transparent)
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_dark)
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
    }
}
