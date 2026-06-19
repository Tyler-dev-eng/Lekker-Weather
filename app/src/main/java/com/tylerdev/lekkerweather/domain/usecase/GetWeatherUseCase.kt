package com.tylerdev.lekkerweather.domain.usecase

import com.tylerdev.lekkerweather.domain.repository.WeatherRepository
import com.tylerdev.lekkerweather.domain.util.Resource
import com.tylerdev.lekkerweather.domain.weather.WeatherInfo
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Resource<WeatherInfo> {
        return repository.getWeatherData(latitude, longitude)
    }
}
