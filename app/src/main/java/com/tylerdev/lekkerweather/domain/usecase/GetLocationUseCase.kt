package com.tylerdev.lekkerweather.domain.usecase

import com.tylerdev.lekkerweather.domain.location.LocationNameProvider
import com.tylerdev.lekkerweather.domain.location.LocationResult
import com.tylerdev.lekkerweather.domain.location.LocationTracker
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationTracker: LocationTracker,
    private val locationNameProvider: LocationNameProvider
) {
    suspend operator fun invoke(): LocationResult? {
        val location = locationTracker.getCurrentLocation() ?: return null
        val name = locationNameProvider.getLocationName(location.latitude, location.longitude)
        return LocationResult(
            latitude = location.latitude,
            longitude = location.longitude,
            name = name
        )
    }
}
