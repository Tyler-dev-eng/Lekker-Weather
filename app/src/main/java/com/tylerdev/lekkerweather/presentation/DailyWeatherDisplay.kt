package com.tylerdev.lekkerweather.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tylerdev.lekkerweather.domain.weather.WeatherData
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyWeatherDisplay(
    dayLabel: String,
    entries: List<WeatherData>,
    modifier: Modifier = Modifier
) {
    val noon = entries.minByOrNull { kotlin.math.abs(it.time.hour - 12) } ?: return
    val minTemp = entries.minOf { it.temperatureCelsius }.roundToInt()
    val maxTemp = entries.maxOf { it.temperatureCelsius }.roundToInt()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(noon.weatherType.animRes)
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dayLabel,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "$minTemp° / $maxTemp°",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}
