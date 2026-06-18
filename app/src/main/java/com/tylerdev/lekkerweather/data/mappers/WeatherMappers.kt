package com.tylerdev.lekkerweather.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.tylerdev.lekkerweather.data.remote.WeatherDataDto
import com.tylerdev.lekkerweather.data.remote.WeatherDto
import com.tylerdev.lekkerweather.domain.weather.WeatherData
import com.tylerdev.lekkerweather.domain.weather.WeatherInfo
import com.tylerdev.lekkerweather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

/**
 * Maps the parallel hourly lists in [WeatherDataDto] into domain [WeatherData] grouped by forecast day.
 *
 * Each map key is the zero-based day index (hour index ÷ 24); values are the hourly readings for that day.
 *
 * @return Hourly [WeatherData] entries keyed by forecast day index.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        IndexedWeatherData(
            index = index,
            data =  WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }.also { println(it) }
}

/**
 * Maps the root forecast [WeatherDto] into [WeatherInfo] for the presentation layer.
 *
 * Groups hourly data by day, then picks [WeatherInfo.currentWeatherData] from today's hours using the
 * current clock (next hour when past the half hour).
 *
 * @return Aggregated forecast snapshot with optional current conditions.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}