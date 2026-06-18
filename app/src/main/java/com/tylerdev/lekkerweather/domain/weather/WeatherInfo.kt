package com.tylerdev.lekkerweather.domain.weather

/**
 * Aggregated forecast snapshot exposed to the presentation layer.
 *
 * Built by repositories after mapping remote DTOs into [WeatherData]. Groups hourly readings by
 * forecast day so the UI can show a multi-day outlook and highlight conditions for right now.
 *
 * @property weatherDataPerDay Hourly [WeatherData] entries keyed by forecast day index.
 * @property currentWeatherData Conditions for the present moment, or null when unavailable.
 */
data class WeatherInfo (
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)