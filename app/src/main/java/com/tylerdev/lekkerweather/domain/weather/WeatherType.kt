package com.tylerdev.lekkerweather.domain.weather

import androidx.annotation.RawRes
import com.tylerdev.lekkerweather.R

/**
 * Normalised weather condition derived from [WMO Weather interpretation codes](https://open-meteo.com/en/docs)
 * (WW codes) returned by Open-Meteo and similar forecast APIs.
 *
 * Each variant pairs a user-facing [weatherDesc] with a Lottie [animRes] for UI rendering.
 * Use [fromWMO] to convert an API weather code into the matching type.
 *
 * @property weatherDesc Human-readable description shown in the app.
 * @property animRes Raw resource ID for the Lottie animation JSON.
 */
sealed class WeatherType(
    val weatherDesc: String,
    @param:RawRes val animRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        animRes = R.raw.anim_clear_sky
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        animRes = R.raw.anim_cloudy
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        animRes = R.raw.anim_partly_cloudy
    )
    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        animRes = R.raw.anim_overcast
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        animRes = R.raw.anim_foggy
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        animRes = R.raw.anim_foggy
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        animRes = R.raw.anim_light_drizzle
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        animRes = R.raw.anim_light_drizzle
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        animRes = R.raw.anim_light_drizzle
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        animRes = R.raw.anim_light_drizzle
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        animRes = R.raw.anim_light_drizzle
    )
    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        animRes = R.raw.anim_rainy
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        animRes = R.raw.anim_rainy
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        animRes = R.raw.anim_heavy_rain
    )
    object HeavyFreezingRain : WeatherType(
        weatherDesc = "Heavy freezing rain",
        animRes = R.raw.anim_heavy_rain
    )
    object SlightSnowFall : WeatherType(
        weatherDesc = "Slight snow fall",
        animRes = R.raw.anim_snowy
    )
    object ModerateSnowFall : WeatherType(
        weatherDesc = "Moderate snow fall",
        animRes = R.raw.anim_heavysnow
    )
    object HeavySnowFall : WeatherType(
        weatherDesc = "Heavy snow fall",
        animRes = R.raw.anim_heavysnow
    )
    object SnowGrains : WeatherType(
        weatherDesc = "Snow grains",
        animRes = R.raw.anim_heavysnow
    )
    object SlightRainShowers : WeatherType(
        weatherDesc = "Slight rain showers",
        animRes = R.raw.anim_heavy_rain
    )
    object ModerateRainShowers : WeatherType(
        weatherDesc = "Moderate rain showers",
        animRes = R.raw.anim_heavy_rain
    )
    object ViolentRainShowers : WeatherType(
        weatherDesc = "Violent rain showers",
        animRes = R.raw.anim_heavy_rain
    )
    object SlightSnowShowers : WeatherType(
        weatherDesc = "Light snow showers",
        animRes = R.raw.anim_snowy
    )
    object HeavySnowShowers : WeatherType(
        weatherDesc = "Heavy snow showers",
        animRes = R.raw.anim_snowy
    )
    object ModerateThunderstorm : WeatherType(
        weatherDesc = "Moderate thunderstorm",
        animRes = R.raw.anim_thunder
    )
    object SlightHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        animRes = R.raw.anim_rainythunder
    )
    object HeavyHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        animRes = R.raw.anim_rainythunder
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
