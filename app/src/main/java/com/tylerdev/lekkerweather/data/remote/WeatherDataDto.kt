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

    @Json(name ="temperature_2m")
    val temperatures: List<Double>,

    @Json(name ="weather_code")
    val weatherCodes: List<Int>,

    @Json(name ="wind_speed_10m")
    val windSpeeds: List<Double>,

    @Json(name ="relative_humidity_2m")
    val humidities: List<Double>,

    @Json(name ="pressure_msl")
    val pressures: List<Double>

)
