package com.tylerdev.lekkerweather.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    val dataPerDay = state.weatherInfo?.weatherDataPerDay ?: return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "This Week",
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        dataPerDay.entries
            .filter { it.key > 0 }
            .sortedBy { it.key }
            .forEach { (_, entries) ->
                val dayLabel = entries.firstOrNull()?.time?.dayOfWeek
                    ?.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    ?: return@forEach
                DailyWeatherDisplay(
                    dayLabel = dayLabel,
                    entries = entries
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
    }
}
