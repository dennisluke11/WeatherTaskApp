package com.example.weathertaskapp.ui.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertaskapp.R
import com.example.weathertaskapp.utils.Dimens

@Composable
fun WeatherCard(
    temperature: Float,
    condition: String,
    city: String,
    sunrise: String,
    sunset: String
) {
    Card(
        elevation = CardDefaults.cardElevation(Dimens.ElevationSmall),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingMedium)
    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingMedium)) {
            Text(
                text = stringResource(R.string.weather_in, city),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeightSmall))

            Text(
                text = stringResource(R.string.temperature_label, temperature),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(R.string.condition_label, condition),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeightMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.WbSunny,
                        contentDescription = stringResource(R.string.sunrise_label),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(stringResource(R.string.sunrise_label), fontSize = 14.sp)
                    Text(sunrise, style = MaterialTheme.typography.bodyMedium)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.NightsStay,
                        contentDescription = stringResource(R.string.sunset_label),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacerHeightExtraSmall))
                    Text(stringResource(R.string.sunset_label), fontSize = 14.sp)
                    Text(sunset, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
