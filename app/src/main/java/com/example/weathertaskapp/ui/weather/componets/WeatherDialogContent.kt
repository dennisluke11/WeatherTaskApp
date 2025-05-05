package com.example.weathertaskapp.ui.weather.componets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weathertaskapp.R

@Composable
fun WeatherDialogContent(
    content: @Composable ColumnScope.() -> Unit,
    onClose: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        content()
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onClose) {
            Text(stringResource(R.string.close_button))
        }
    }
}