package com.tylerdev.lekkerweather.domain.weather

import androidx.annotation.DrawableRes
import com.tylerdev.lekkerweather.R

/**
 * Normalised weather condition derived from [WMO Weather interpretation codes](https://open-meteo.com/en/docs)
 * (WW codes) returned by Open-Meteo and similar forecast APIs.
 *
 * Each variant pairs a user-facing [weatherDesc] with a drawable [iconRes] for UI rendering.
 * Use [fromWMO] to convert an API weather code into the matching type.
 *
 * @property weatherDesc Human-readable description shown in the app.
 * @property iconRes Drawable resource ID for the weather icon.
 */
sealed class WeatherType(
    val weatherDesc: String,
    @param:DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_sunny
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_cloudy
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_cloudy
    )
    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.ic_cloudy
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconRes = R.drawable.ic_very_cloudy
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconRes = R.drawable.ic_very_cloudy
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        iconRes = R.drawable.ic_rainshower
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        iconRes = R.drawable.ic_rainshower
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy
    )
    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        iconRes = R.drawable.ic_rainy
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.ic_rainy
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        iconRes = R.drawable.ic_rainy
    )
    object HeavyFreezingRain : WeatherType(
        weatherDesc = "Heavy freezing rain",
        iconRes = R.drawable.ic_snowyrainy
    )
    object SlightSnowFall : WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.ic_snowy
    )
    object ModerateSnowFall : WeatherType(
        weatherDesc = "Moderate snow fall",
        iconRes = R.drawable.ic_heavysnow
    )
    object HeavySnowFall : WeatherType(
        weatherDesc = "Heavy snow fall",
        iconRes = R.drawable.ic_heavysnow
    )
    object SnowGrains : WeatherType(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.ic_heavysnow
    )
    object SlightRainShowers : WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateRainShowers : WeatherType(
        weatherDesc = "Moderate rain showers",
        iconRes = R.drawable.ic_rainshower
    )
    object ViolentRainShowers : WeatherType(
        weatherDesc = "Violent rain showers",
        iconRes = R.drawable.ic_rainshower
    )
    object SlightSnowShowers : WeatherType(
        weatherDesc = "Light snow showers",
        iconRes = R.drawable.ic_snowy
    )
    object HeavySnowShowers : WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.ic_snowy
    )
    object ModerateThunderstorm : WeatherType(
        weatherDesc = "Moderate thunderstorm",
        iconRes = R.drawable.ic_thunder
    )
    object SlightHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = R.drawable.ic_rainythunder
    )
    object HeavyHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        /**
         * Maps a WMO weather interpretation code to the corresponding [WeatherType].
         *
         * Unrecognized codes fall back to [ClearSky].
         *
         * @param code WMO weather code from the API (e.g. Open-Meteo `weather_code`).
         * @return The matching [WeatherType], or [ClearSky] when [code] is unknown.
         */
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}
