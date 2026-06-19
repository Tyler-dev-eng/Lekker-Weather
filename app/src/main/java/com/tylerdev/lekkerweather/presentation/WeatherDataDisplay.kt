package com.tylerdev.lekkerweather.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/**
 * Compact row that displays a weather metric with an icon, value, and unit.
 *
 * Used inside [WeatherCard] for pressure, humidity, and wind speed.
 *
 * @param value Numeric reading to show.
 * @param unit Suffix appended to [value] (e.g. `"hpa"`, `"%"`, `"km/h"`).
 * @param modifier Optional layout modifier for the row.
 * @param textStyle Typography for the value and unit text.
 * @param icon Leading icon representing the metric.
 * @param iconTint Colour applied to [icon].
 */
@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    icon: ImageVector,
    iconTint: Color = Color.White
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
            style = textStyle
        )
    }
}