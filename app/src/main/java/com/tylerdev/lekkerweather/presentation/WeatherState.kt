package com.tylerdev.lekkerweather.presentation

import com.tylerdev.lekkerweather.domain.weather.WeatherInfo

/**
 * UI state for the weather screen.
 *
 * Held and updated by [WeatherViewModel]; Compose reads these fields to show forecast content,
 * a loading indicator, or an error message.
 *
 * @property weatherInfo Loaded forecast data, or null before a successful fetch or after an error.
 * @property isLoading True while location or weather data is being fetched.
 * @property error User-visible error message, or null when there is no error.
 */
data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationName: String? = null
)