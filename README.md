# lekkerWeather

A clean, modern Android weather app built with Jetpack Compose. Shows current conditions, an hourly forecast, and a 7-day outlook for your location — with day and night animations that switch based on real sunrise/sunset data from the API.

---

## Features

- Current conditions — temperature, feels-like, weather description, pressure, humidity, wind speed
- Hourly forecast strip for today
- 7-day weekly forecast with min/max temperatures and dominant daytime condition
- Day and night Lottie animations driven by Open-Meteo's `is_day` field
- Reverse-geocoded city name shown on the weather card
- Pull-to-refresh
- Error screen with retry for location or network failures
- Edge-to-edge UI with correct status bar icon colours

## Stack

| Layer        | Technology                                     |
|--------------|------------------------------------------------|
| UI           | Jetpack Compose + Material 3                   |
| Animations   | Lottie (Meteocons)                             |
| DI           | Hilt                                           |
| Networking   | Retrofit + OkHttp + Moshi                      |
| Location     | Google Play Services — Fused Location Provider |
| Geocoding    | Android `Geocoder`                             |
| Architecture | Clean Architecture + MVVM                      |

## Architecture

The project follows Clean Architecture with three layers:

```
data  →  domain  ←  presentation
```

- `domain` is pure Kotlin — no Android dependencies
- `data` implements domain interfaces (repository, location tracker, geocoder)
- `presentation` reads `WeatherState` from the ViewModel and renders it

See [ARCHITECTURE.md](ARCHITECTURE.md) for a full breakdown of every file and package.

## Setup

1. Clone the repo
2. Open in Android Studio
3. Add your `local.properties` if needed (no API key required — uses [Open-Meteo](https://open-meteo.com), which is free and open)
4. Run on a device or emulator with location enabled

## Weather Data

Powered by [Open-Meteo](https://open-meteo.com) — free, no API key required.

Hourly fields fetched: `temperature_2m`, `weather_code`, `relative_humidity_2m`, `wind_speed_10m`, `pressure_msl`, `is_day`.

WMO weather codes are mapped to normalised `WeatherType` variants in `domain/weather/WeatherType.kt`.

## Animations

Weather animations are from [Meteocons](https://bas.dev/work/meteocons) by Bas Milius. Day and night variants are stored in `res/raw/` and selected per-hour using the `is_day` field returned by the API.
