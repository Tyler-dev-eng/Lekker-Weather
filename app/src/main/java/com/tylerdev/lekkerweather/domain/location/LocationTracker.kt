package com.tylerdev.lekkerweather.domain.location

import android.location.Location

/**
 * Domain contract for reading the device's current geographic position.
 *
 * Presentation and use cases depend on this interface rather than Android location APIs. Implementations
 * live in the data layer and resolve coordinates when permissions and location services allow it.
 */
interface LocationTracker {

    /**
     * Returns the device's last known location, or null when it cannot be determined.
     *
     * @return Current [Location] with latitude and longitude, or null if unavailable.
     */
    suspend fun getCurrentLocation(): Location?
}