package com.tylerdev.lekkerweather.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.tylerdev.lekkerweather.data.mappers.toWeatherInfo
import com.tylerdev.lekkerweather.data.remote.WeatherApi
import com.tylerdev.lekkerweather.domain.repository.WeatherRepository
import com.tylerdev.lekkerweather.domain.util.Resource
import com.tylerdev.lekkerweather.domain.weather.WeatherInfo
import javax.inject.Inject

/**
 * Data-layer implementation of [WeatherRepository].
 *
 * Fetches forecast data via [WeatherApi], maps the response into domain [WeatherInfo], and wraps the
 * outcome in [Resource] so callers can handle success and failure without dealing with exceptions.
 */
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    /**
     * @see WeatherRepository.getWeatherData
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(
        lat: Double,
        long: Double
    ): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(lat = lat, long = long).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
