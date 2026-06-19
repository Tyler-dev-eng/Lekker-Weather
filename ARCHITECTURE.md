# Architecture

lekkerWeather follows **Clean Architecture** with three layers: `data`, `domain`, and `presentation`. Each layer has a strict dependency direction — `presentation` depends on `domain`, `data` depends on `domain`, and `domain` depends on nothing.

```
app/src/main/java/com/tylerdev/lekkerweather/
├── WeatherApp.kt
├── data/
│   ├── location/
│   ├── mappers/
│   ├── remote/
│   └── repository/
├── di/
├── domain/
│   ├── location/
│   ├── repository/
│   ├── usecase/
│   ├── util/
│   └── weather/
└── presentation/
    └── ui/theme/
```

---

## Root

| File            | Purpose                                                                                                                           |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `WeatherApp.kt` | `Application` subclass annotated with `@HiltAndroidApp`. Required for Hilt to generate the component graph from modules in `di/`. |

---

## `data/`

Responsible for all I/O. Nothing in this layer leaks into `domain` or `presentation` — raw API types are mapped into domain models before crossing the boundary.

### `data/location/`

Data-layer implementations of domain location contracts.

| File                              | Purpose                                                                                                                                                                                                                                      |
|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `DefaultLocationTracker.kt`       | Uses `FusedLocationProviderClient` to return the device's last known location. Checks fine/coarse permissions and verifies that GPS or network providers are enabled before requesting a fix; returns null when any prerequisite is missing. |
| `GeocoderLocationNameProvider.kt` | Implements `LocationNameProvider` using Android's `Geocoder` to reverse-geocode a lat/lon into a city name. Handles the API 33+ async path and the legacy synchronous path on a background dispatcher.                                       |

### `data/mappers/`

Extension functions that translate remote DTOs into domain models. Keeping mapping logic here prevents it from leaking into either the repository or the DTOs themselves.

| File                | Purpose                                                                                                                                                                                                                                                  |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `WeatherMappers.kt` | `WeatherDataDto.toWeatherDataMap()` — zips the parallel hourly lists into `WeatherData` objects grouped by forecast day index. `WeatherDto.toWeatherInfo()` — builds the full `WeatherInfo` snapshot, including resolving the current hour's conditions. |

**What goes here as the project grows:** additional `*Mappers.kt` files for each new remote data source or DTO type.

### `data/remote/`

Retrofit interface and Moshi DTOs for the [Open-Meteo](https://open-meteo.com) forecast API.

| File                | Purpose                                                                                                                                                               |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `WeatherApi.kt`     | Retrofit interface declaring the `GET /v1/forecast` endpoint. Takes latitude/longitude, returns `WeatherDto`.                                                         |
| `WeatherDto.kt`     | Root Moshi model for the API response. Wraps the `hourly` block.                                                                                                      |
| `WeatherDataDto.kt` | Moshi model for the `hourly` block. Parallel lists of timestamps, temperatures, weather codes, wind speeds, humidities, and pressures — all indexed by forecast hour. |

**What goes here as the project grows:** additional `*Dto` classes for new endpoints.

### `data/repository/`

Concrete implementations of domain repository interfaces. Wires together the remote API and mappers; wraps results in `Resource`.

| File                       | Purpose                                                                                                                                               |
|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| `WeatherRepositoryImpl.kt` | Implements `WeatherRepository`. Calls `WeatherApi`, maps the response via `WeatherDto.toWeatherInfo()`, and catches exceptions into `Resource.Error`. |

**What goes here as the project grows:** additional `*RepositoryImpl` classes, local Room data source calls alongside remote ones.

---

## `di/`

Hilt modules that wire the dependency graph. All modules install into `SingletonComponent` so dependencies are app-scoped singletons.

| File                  | Purpose                                                                                                                                                                |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `AppModule.kt`        | Provides the Retrofit-backed `WeatherApi` (with OkHttp logging interceptor) pointed at `api.open-meteo.com`, and the `FusedLocationProviderClient` from Play Services. |
| `LocationModule.kt`   | Binds `DefaultLocationTracker` as the implementation of `LocationTracker`, and `GeocoderLocationNameProvider` as the implementation of `LocationNameProvider`.         |
| `RepositoryModule.kt` | Binds `WeatherRepositoryImpl` as the implementation of `WeatherRepository`.                                                                                            |

**What goes here as the project grows:** additional modules for new data sources (e.g. a `DatabaseModule` for Room).

---

## `domain/`

Pure Kotlin business logic with no Android or framework dependencies. This is the stable core — `data` and `presentation` both depend on it, never the other way around.

### `domain/location/`

| File                      | Purpose                                                                                                                                        |
|---------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| `LocationTracker.kt`      | Domain contract for reading the device's current position. Returns a nullable `Location`; implementations live in `data/location/`.            |
| `LocationNameProvider.kt` | Domain contract for reverse-geocoding a lat/lon into a human-readable city name. Returns a nullable `String`; implemented in `data/location/`. |
| `LocationResult.kt`       | Data class bundling the resolved `latitude`, `longitude`, and optional `name` from `GetLocationUseCase`.                                       |

### `domain/repository/`

Repository interfaces owned by the domain. Presentation code depends on these contracts, not on any data-layer class.

| File                   | Purpose                                                                                                                                           |
|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| `WeatherRepository.kt` | Declares `getWeatherData(lat, long)` returning `Resource<WeatherInfo>`. The single point of contact between domain/presentation and weather data. |

**What goes here as the project grows:** additional repository interfaces for new data sources.

### `domain/util/`

General-purpose utilities shared across the domain.

| File          | Purpose                                                                                                                                                                   |
|---------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Resource.kt` | Sealed class wrapping async operation outcomes into `Success`, `Loading`, and `Error` states. Repositories return `Resource<T>`; ViewModels map each variant to UI state. |

### `domain/usecase/`

Application-level business logic. Each use case has a single responsibility and is invoked via `operator fun invoke()` so call sites read like a function call.

| File                    | Purpose                                                                                                                                                          |
|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `GetLocationUseCase.kt` | Resolves the device's current location via `LocationTracker`, then reverse-geocodes it via `LocationNameProvider`. Returns `LocationResult?` (null = unavailable). |
| `GetWeatherUseCase.kt`  | Fetches the forecast for a given lat/lon via `WeatherRepository`. Returns `Resource<WeatherInfo>`.                                                               |

### `domain/weather/`

Core weather domain types.

| File             | Purpose                                                                                                                                                                                                                                                                                                            |
|------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `WeatherData.kt` | Domain model for weather conditions at a single forecast hour. Holds time, temperature, pressure, humidity, wind speed, `isDay` (from the Open-Meteo `is_day` field), and a `WeatherType`.                                                                                                                         |
| `WeatherInfo.kt` | Aggregated forecast snapshot exposed to the presentation layer. Contains hourly `WeatherData` entries keyed by forecast day index, plus the current hour's conditions.                                                                                                                                             |
| `WeatherType.kt` | Sealed class of normalised weather conditions derived from WMO weather codes. Each variant holds a user-facing description and separate day/night Lottie animation resource IDs (`animRes`, `nightAnimRes`). `animResFor(isNight)` picks the correct animation. `fromWMO(code)` converts a raw API code to a type. |

---

## `presentation/`

Jetpack Compose UI layer. Observes domain state and renders it — no business logic lives here.

| File                      | Purpose                                                                                                                                                                                                                                     |
|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `MainActivity.kt`         | Single-activity entry point. Hosts a `PullToRefreshBox` wrapping a `LazyColumn` with `WeatherCard`, `WeatherForecast`, and `WeeklyForecast`. Handles edge-to-edge insets via `navigationBars` content padding.                              |
| `WeatherViewModel.kt`     | `@HiltViewModel` that delegates to `GetLocationUseCase` and `GetWeatherUseCase`, then maps results into `WeatherState` via Compose `mutableStateOf`. Contains no business logic.                                                           |
| `WeatherState.kt`         | UI state data class holding `weatherInfo`, `isLoading`, `error`, and `locationName`. The single source of truth Compose reads to render the weather screen.                                                                                 |
| `WeatherCard.kt`          | Card showing current conditions — location pin + city name, time, Lottie animation (day or night), temperature, description, and the three `WeatherDataDisplay` metrics. Pressure animation switches between high/low based on the reading. |
| `WeatherDataDisplay.kt`   | Reusable row displaying a single weather metric as a Lottie animation + value + unit. Supports an optional `animationSize` override and an optional `animationTint` colour filter.                                                          |
| `WeatherForecast.kt`      | Horizontal `LazyRow` of today's hourly forecast using `HourlyWeatherDisplay`.                                                                                                                                                               |
| `HourlyWeatherDisplay.kt` | Single hourly slot: time, Lottie animation (day or night driven by `WeatherData.isDay`), and temperature.                                                                                                                                   |
| `WeeklyForecast.kt`       | Vertical list of days 1–6 from `weatherDataPerDay`, each rendered by `DailyWeatherDisplay`.                                                                                                                                                 |
| `DailyWeatherDisplay.kt`  | Single daily row: day name, noon-representative Lottie animation (daytime), and min°/max° temperature range derived from all hourly entries for that day.                                                                                   |

### `presentation/ui/theme/`

Material 3 theming applied app-wide.

| File       | Purpose                                                                                        |
|------------|------------------------------------------------------------------------------------------------|
| `Color.kt` | Color palette definitions.                                                                     |
| `Theme.kt` | `LekkerWeatherTheme` composable — wires colors, typography, and shapes into a `MaterialTheme`. |
| `Type.kt`  | Typography scale.                                                                              |

**What goes here as the project grows:** screen composables, ViewModels, UI state classes, navigation setup, reusable components.

---

## `res/`

### `res/raw/`

Lottie animation JSON files. Weather condition animations come in day (`anim_*.json`) and night variants, referenced by `WeatherType` via `@RawRes` IDs. Utility animations (`circle_loader.json`) are also stored here.

### `res/drawable/`

Static vector icon fallbacks (`ic_*.xml`) for weather conditions and UI elements (wind, pressure, rain drop).

### `res/values/` and `res/values-v31/`

`colors.xml` defines the app palette including `splash_background` (`#2D7DB4`). `themes.xml` sets `windowBackground` for pre-API-31 devices; the `values-v31` override adds `windowSplashScreenBackground` for the Android 12+ splash screen API.

### `res/mipmap-*/`

Launcher icons at each screen density.
