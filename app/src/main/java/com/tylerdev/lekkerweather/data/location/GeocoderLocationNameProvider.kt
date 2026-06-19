package com.tylerdev.lekkerweather.data.location

import android.app.Application
import android.location.Geocoder
import android.os.Build
import com.tylerdev.lekkerweather.domain.location.LocationNameProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeocoderLocationNameProvider @Inject constructor(
    private val application: Application
) : LocationNameProvider {

    override suspend fun getLocationName(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(application)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine { continuation ->
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    continuation.resume(addresses.firstOrNull()?.locality)
                }
            }
        } else {
            @Suppress("DEPRECATION")
            withContext(Dispatchers.IO) {
                geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()?.locality
            }
        }
    }
}
