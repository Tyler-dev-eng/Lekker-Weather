package com.tylerdev.lekkerweather.data.remote

import com.squareup.moshi.Json

/**
 * Root Moshi model for the forecast API response.
 *
 * Deserialised from the JSON returned by [WeatherApi.getWeatherData]. The nested [weatherData]
 * holds the hourly series that repositories map into domain models.
 *
 * @property weatherData Hourly forecast measurements for the requested location.
 */
data class WeatherDto (

    @Json(name = "hourly")
    val weatherData: WeatherDataDto
)