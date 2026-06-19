package com.tylerdev.lekkerweather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerdev.lekkerweather.domain.usecase.GetLocationUseCase
import com.tylerdev.lekkerweather.domain.usecase.GetWeatherUseCase
import com.tylerdev.lekkerweather.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getLocation: GetLocationUseCase,
    private val getWeather: GetWeatherUseCase
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            val location = getLocation()
            if (location == null) {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
                return@launch
            }

            when (val result = getWeather(location.latitude, location.longitude)) {
                is Resource.Success -> state = state.copy(
                    weatherInfo = result.data,
                    locationName = location.name,
                    isLoading = false,
                    error = null
                )
                is Resource.Error -> state = state.copy(
                    weatherInfo = null,
                    isLoading = false,
                    error = result.message
                )
                is Resource.Loading -> state = state.copy(
                    weatherInfo = null,
                    isLoading = true,
                    error = null
                )
            }
        }
    }
}
