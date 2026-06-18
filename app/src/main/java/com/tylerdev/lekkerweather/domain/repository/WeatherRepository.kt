package com.tylerdev.lekkerweather.domain.repository

import com.tylerdev.lekkerweather.domain.util.Resource
import com.tylerdev.lekkerweather.domain.weather.WeatherInfo

/**
 * Domain contract for loading weather forecast data.
 *
 * Presentation code depends on this interface rather than remote APIs or DTOs. Implementations live
 * in the data layer and map network responses into [WeatherInfo] wrapped in [Resource].
 */
interface WeatherRepository {

    /**
     * Loads forecast data for the coordinates supplied.
     *
     * @param lat Latitude of the location.
     * @param long Longitude of the location.
     * @return [Resource.Success] with [WeatherInfo] on success, or [Resource.Error] when the fetch fails.
     */
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}