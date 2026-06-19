package com.tylerdev.lekkerweather.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * Compact row that displays a weather metric with a Lottie animation, value, and unit.
 *
 * Used inside [WeatherCard] for pressure, humidity, and wind speed. The leading animation loops
 * continuously; an optional tint can be applied via [animationTint].
 *
 * @param value Numeric reading to show.
 * @param unit Suffix appended to [value] (e.g. `"hpa"`, `"%"`, `"km/h"`).
 * @param modifier Optional layout modifier for the row.
 * @param textStyle Typography for the value and unit text.
 * @param animation Raw Lottie resource ID for the metric animation.
 * @param animationSize Size of the Lottie animation; defaults to 25.dp.
 * @param animationTint Optional colour tint applied to the animation, or null for the asset's default colours.
 */
@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    animation: Int,
    animationSize: Dp = 25.dp,
    animationTint: Color? = null,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation)
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(animationSize)
                .then(
                    if (animationTint != null) Modifier.graphicsLayer {
                        colorFilter = ColorFilter.tint(animationTint, BlendMode.SrcAtop)
                    } else Modifier
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
            style = textStyle
        )
    }
}