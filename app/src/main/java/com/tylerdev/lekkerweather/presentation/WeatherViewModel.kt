package com.tylerdev.lekkerweather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerdev.lekkerweather.domain.location.LocationTracker
import com.tylerdev.lekkerweather.domain.repository.WeatherRepository
import com.tylerdev.lekkerweather.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the weather screen.
 *
 * Resolves the device's location, fetches forecast data through [WeatherRepository], and exposes
 * [state] for Compose UI to render loading, success, and error outcomes.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    /** Observable UI state for the weather screen. Updated only from this ViewModel. */
    var state by mutableStateOf(WeatherState())
        private set

    /**
     * Loads forecast data for the current location.
     *
     * Sets [WeatherState.isLoading] while fetching. On success, populates [WeatherState.weatherInfo];
     * on failure, sets [WeatherState.error] (including when location cannot be obtained).
     */
    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = true,
                            error = null
                        )
                    }
                }
            } ?: run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}
