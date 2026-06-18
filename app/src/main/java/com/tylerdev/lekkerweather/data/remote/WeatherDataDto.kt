package com.tylerdev.lekkerweather.data.remote

import com.squareup.moshi.Json

/**
 * Moshi model for the `hourly` block of the forecast API response.
 *
 * Each property is a parallel list of values for successive forecast hours; index `i` across
 * [time], [temperatures], [weatherCodes], and the other fields refers to the same hour.
 * Repositories map this DTO into domain types before exposing it to the UI layer.
 *
 * @property time ISO timestamps for each forecast hour.
 * @property temperatures Air temperature at 2 m, in °C.
 * @property weatherCodes WMO weather interpretation codes.
 * @property windSpeeds Wind speed at 10 m, in km/h.
 * @property humidities Relative humidity at 2 m, as a percentage.
 * @property pressures Mean sea-level pressure, in hPa.
 */
data class WeatherDataDto(
    val time: List<String>,

    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,

    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,

    @field:Json(name = "windspeed_10m")
    val windSpeeds: List<Double>,

    @field:Json(name = "relativehumidity_2m")
    val humidities: List<Double>,

    @field:Json(name = "pressure_msl")
    val pressures: List<Double>

)
