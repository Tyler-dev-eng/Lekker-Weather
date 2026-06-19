package com.tylerdev.lekkerweather.domain.location

/**
 * Domain contract for resolving a human-readable location name from coordinates.
 */
interface LocationNameProvider {
    suspend fun getLocationName(latitude: Double, longitude: Double): String?
}
