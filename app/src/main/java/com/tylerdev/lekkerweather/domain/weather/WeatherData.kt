package com.tylerdev.lekkerweather.domain.weather

import java.time.LocalDateTime

/**
 * Domain model for weather conditions at a single forecast hour.
 *
 * Produced when repositories map remote DTOs (e.g. [com.tylerdev.lekkerweather.data.remote.WeatherDataDto])
 * into app types. Aggregated in [WeatherInfo] as the current conditions or as hourly entries per day.
 *
 * @property time When this forecast hour occurs.
 * @property temperatureCelsius Air temperature in °C.
 * @property pressure Mean sea-level pressure in hPa.
 * @property humidity Relative humidity as a percentage.
 * @property windSpeed Wind speed in km/h.
 * @property weatherType Normalised condition for UI display and animation.
 */
data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val humidity: Double,
    val windSpeed: Double,
    val weatherType: WeatherType,
    val isDay: Boolean,
)
