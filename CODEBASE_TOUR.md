# Lekker Weather — Codebase Tour

A structured reading order that follows the data flow from the outside in, then back out to the UI.

---

## Mental Model

```
MainActivity
  └── WeatherViewModel
        ├── GetLocationUseCase  →  LocationTracker (FusedLocation)
        │                          LocationNameProvider (Geocoder)
        └── GetWeatherUseCase   →  WeatherRepository
                                      └── WeatherApi (Retrofit)
                                          WeatherMappers (DTO → domain)
```

The ViewModel never touches Retrofit or GPS directly — it only talks to use cases, which only talk to interfaces, which are injected by Hilt with their real implementations. That's the Clean Architecture boundary in practice.

---

## Layer 1 — Domain

> The "what". No Android dependencies. Everything else depends on this layer; it depends on nothing.

| # | File | Purpose |
|---|------|---------|
| 1 | `domain/util/Resource.kt` | Generic `Success/Error/Loading` wrapper used everywhere |
| 2 | `domain/weather/WeatherData.kt` | Core model — what a single weather observation looks like |
| 3 | `domain/weather/WeatherInfo.kt` | Groups `WeatherData` into current + hourly + daily |
| 4 | `domain/weather/WeatherType.kt` | WMO weather code → description + icon mapping |
| 5 | `domain/location/LocationResult.kt` | Sealed class for location outcomes |
| 6 | `domain/location/LocationTracker.kt` | Interface: "give me a location" |
| 7 | `domain/location/LocationNameProvider.kt` | Interface: "turn coordinates into a city name" |
| 8 | `domain/repository/WeatherRepository.kt` | Interface: "give me weather for lat/lon" |
| 9 | `domain/usecase/GetLocationUseCase.kt` | Orchestrates location fetching |
| 10 | `domain/usecase/GetWeatherUseCase.kt` | Orchestrates weather fetching |

---

## Layer 2 — Data

> The "how". Fulfills the contracts defined in the domain layer.

| # | File | Purpose |
|---|------|---------|
| 11 | `data/remote/WeatherDto.kt` + `WeatherDataDto.kt` | Raw API response shapes (JSON → Kotlin) |
| 12 | `data/remote/WeatherApi.kt` | Retrofit interface — the actual HTTP call |
| 13 | `data/mappers/WeatherMappers.kt` | Translates DTOs → domain models (most logic-heavy file in this layer) |
| 14 | `data/repository/WeatherRepositoryImpl.kt` | Implements `WeatherRepository` using the API + mappers |
| 15 | `data/location/DefaultLocationTracker.kt` | Implements `LocationTracker` using FusedLocationProvider |
| 16 | `data/location/GeocoderLocationNameProvider.kt` | Implements `LocationNameProvider` using `Geocoder` |

---

## Layer 3 — DI

> The wiring. Shows how Hilt connects interfaces to their implementations.

| # | File | Purpose |
|---|------|---------|
| 17 | `di/AppModule.kt` | Provides Retrofit, `WeatherApi` |
| 18 | `di/RepositoryModule.kt` | Binds `WeatherRepositoryImpl` → `WeatherRepository` |
| 19 | `di/LocationModule.kt` | Binds location tracker + name provider |

---

## Layer 4 — Presentation

> The "show". How state flows from the ViewModel to the screen.

| # | File | Purpose |
|---|------|---------|
| 20 | `presentation/WeatherState.kt` | Single UI state object the screen observes |
| 21 | `presentation/WeatherViewModel.kt` | Calls use cases, emits `WeatherState` via `StateFlow` |
| 22 | `presentation/MainActivity.kt` | Entry point — collects state, handles permissions, sets content |
| 23 | `presentation/WeatherCard.kt` | Current conditions card |
| 24 | `presentation/WeatherDataDisplay.kt` | Reusable row for a single metric (wind, humidity, etc.) |
| 25 | `presentation/HourlyWeatherDisplay.kt` | Single hour item in the horizontal scroll |
| 26 | `presentation/WeatherForecast.kt` | Hourly forecast strip |
| 27 | `presentation/DailyWeatherDisplay.kt` | Single day row |
| 28 | `presentation/WeeklyForecast.kt` | 7-day forecast list |
| 29 | `presentation/ErrorScreen.kt` | Error state UI |
| 30 | `WeatherApp.kt` | `@HiltAndroidApp` application class — just the bootstrap |
