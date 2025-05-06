package com.example.weathertaskapp.ui.weather.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weathertaskapp.R
import com.example.weathertaskapp.utils.Dimens

@Composable
fun WeatherDialogContent(
    content: @Composable ColumnScope.() -> Unit,
    onClose: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        content()
        Spacer(modifier = Modifier.height(Dimens.SpacerHeightSmall))
        Button(onClick = onClose) {
            Text(stringResource(R.string.close_button))
        }
    }
}