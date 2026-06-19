package com.tylerdev.lekkerweather.domain.weather

import androidx.annotation.RawRes
import com.tylerdev.lekkerweather.R

/**
 * Normalised weather condition derived from [WMO Weather interpretation codes](https://open-meteo.com/en/docs)
 * (WW codes) returned by Open-Meteo and similar forecast APIs.
 *
 * Each variant pairs a user-facing [weatherDesc] with day and night Lottie animations.
 * Use [animResFor] to pick the correct animation, and [fromWMO] to map an API code.
 *
 * @property weatherDesc Human-readable description shown in the app.
 * @property animRes Raw resource ID for the daytime Lottie animation.
 * @property nightAnimRes Raw resource ID for the night-time Lottie animation.
 */
sealed class WeatherType(
    val weatherDesc: String,
    @param:RawRes val animRes: Int,
    @param:RawRes val nightAnimRes: Int
) {
    fun animResFor(isNight: Boolean) = if (isNight) nightAnimRes else animRes

    object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        animRes = R.raw.anim_clear_sky,
        nightAnimRes = R.raw.anim_clear_night
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        animRes = R.raw.anim_cloudy,
        nightAnimRes = R.raw.anim_mostly_clear_night
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        animRes = R.raw.anim_partly_cloudy,
        nightAnimRes = R.raw.anim_partly_cloudy_night
    )
    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        animRes = R.raw.anim_overcast,
        nightAnimRes = R.raw.anim_overcast_night
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        animRes = R.raw.anim_foggy,
        nightAnimRes = R.raw.anim_fog_night
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        animRes = R.raw.anim_foggy,
        nightAnimRes = R.raw.anim_fog_night
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        animRes = R.raw.anim_light_drizzle,
        nightAnimRes = R.raw.anim_mostly_clear_drizzle_night
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        animRes = R.raw.anim_light_drizzle,
        nightAnimRes = R.raw.anim_mostly_clear_drizzle_night
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        animRes = R.raw.anim_light_drizzle,
        nightAnimRes = R.raw.anim_overcast_drizzle_night
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        animRes = R.raw.anim_light_drizzle,
        nightAnimRes = R.raw.anim_overcast_drizzle_night
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        animRes = R.raw.anim_light_drizzle,
        nightAnimRes = R.raw.anim_overcast_drizzle_night
    )
    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        animRes = R.raw.anim_rainy,
        nightAnimRes = R.raw.anim_mostly_clear_rain_night
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        animRes = R.raw.anim_rainy,
        nightAnimRes = R.raw.anim_mostly_clear_rain_night
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        animRes = R.raw.anim_heavy_rain,
        nightAnimRes = R.raw.anim_overcast_rain_night
    )
    object HeavyFreezingRain : WeatherType(
        weatherDesc = "Heavy freezing rain",
        animRes = R.raw.anim_heavy_rain,
        nightAnimRes = R.raw.anim_overcast_rain_night
    )
    object SlightSnowFall : WeatherType(
        weatherDesc = "Slight snow fall",
        animRes = R.raw.anim_snowy,
        nightAnimRes = R.raw.anim_mostly_clear_snow_night
    )
    object ModerateSnowFall : WeatherType(
        weatherDesc = "Moderate snow fall",
        animRes = R.raw.anim_heavysnow,
        nightAnimRes = R.raw.anim_overcast_snow_night
    )
    object HeavySnowFall : WeatherType(
        weatherDesc = "Heavy snow fall",
        animRes = R.raw.anim_heavysnow,
        nightAnimRes = R.raw.anim_overcast_snow_night
    )
    object SnowGrains : WeatherType(
        weatherDesc = "Snow grains",
        animRes = R.raw.anim_heavysnow,
        nightAnimRes = R.raw.anim_overcast_snow_night
    )
    object SlightRainShowers : WeatherType(
        weatherDesc = "Slight rain showers",
        animRes = R.raw.anim_heavy_rain,
        nightAnimRes = R.raw.anim_mostly_clear_rain_night
    )
    object ModerateRainShowers : WeatherType(
        weatherDesc = "Moderate rain showers",
        animRes = R.raw.anim_heavy_rain,
        nightAnimRes = R.raw.anim_overcast_rain_night
    )
    object ViolentRainShowers : WeatherType(
        weatherDesc = "Violent rain showers",
        animRes = R.raw.anim_heavy_rain,
        nightAnimRes = R.raw.anim_overcast_rain_night
    )
    object SlightSnowShowers : WeatherType(
        weatherDesc = "Light snow showers",
        animRes = R.raw.anim_snowy,
        nightAnimRes = R.raw.anim_mostly_clear_snow_night
    )
    object HeavySnowShowers : WeatherType(
        weatherDesc = "Heavy snow showers",
        animRes = R.raw.anim_snowy,
        nightAnimRes = R.raw.anim_overcast_snow_night
    )
    object ModerateThunderstorm : WeatherType(
        weatherDesc = "Moderate thunderstorm",
        animRes = R.raw.anim_thunder,
        nightAnimRes = R.raw.thunderstorms_night
    )
    object SlightHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        animRes = R.raw.anim_rainythunder,
        nightAnimRes = R.raw.thunderstorms_overcast_rain_night
    )
    object HeavyHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        animRes = R.raw.anim_rainythunder,
        nightAnimRes = R.raw.thunderstorms_overcast_rain_night
    )

    companion object {
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
