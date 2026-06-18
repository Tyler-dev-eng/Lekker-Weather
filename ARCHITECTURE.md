# Architecture

lekkerWeather follows **Clean Architecture** with three layers: `data`, `domain`, and `presentation`. Each layer has a strict dependency direction — `presentation` depends on `domain`, `data` depends on `domain`, and `domain` depends on nothing.

```
app/src/main/java/com/tylerdev/lekkerweather/
├── data/
│   ├── mappers/
│   └── remote/
├── domain/
│   ├── util/
│   └── weather/
└── presentation/
    └── ui/theme/
```

---

## `data/`

Responsible for all I/O. Nothing in this layer leaks into `domain` or `presentation` — raw API types are mapped into domain models before crossing the boundary.

### `data/mappers/`

Extension functions that translate remote DTOs into domain models. Keeping mapping logic here prevents it from leaking into either the repository or the DTOs themselves.

| File | Purpose |
|---|---|
| `WeatherMappers.kt` | `WeatherDataDto.toWeatherDataMap()` — zips the parallel hourly lists into `WeatherData` objects grouped by forecast day index. `WeatherDto.toWeatherInfo()` — builds the full `WeatherInfo` snapshot, including resolving the current hour's conditions. |

**What goes here as the project grows:** additional `*Mappers.kt` files for each new remote data source or DTO type.

---

### `data/remote/`

Retrofit interface and Moshi DTOs for the [Open-Meteo](https://open-meteo.com) forecast API.

| File | Purpose |
|---|---|
| `WeatherApi.kt` | Retrofit interface declaring the `GET /v1/forecast` endpoint. Takes latitude/longitude, returns `WeatherDto`. |
| `WeatherDto.kt` | Root Moshi model for the API response. Wraps the `hourly` block. |
| `WeatherDataDto.kt` | Moshi model for the `hourly` block. Parallel lists of timestamps, temperatures, weather codes, wind speeds, humidities, and pressures — all indexed by forecast hour. |

**What goes here as the project grows:** additional `*Dto` classes for new endpoints, Retrofit/OkHttp setup (e.g. a `NetworkModule`), repository implementations.

---

## `domain/`

Pure Kotlin business logic with no Android or framework dependencies. This is the stable core — `data` and `presentation` both depend on it, never the other way around.

### `domain/util/`

General-purpose utilities shared across the domain.

| File | Purpose |
|---|---|
| `Resource.kt` | Sealed class wrapping async operation outcomes into `Success`, `Loading`, and `Error` states. Repositories return `Resource<T>`; ViewModels map each variant to UI state. |

### `domain/weather/`

Core weather domain types.

| File | Purpose |
|---|---|
| `WeatherData.kt` | Domain model for weather conditions at a single forecast hour. Holds time, temperature, pressure, humidity, wind speed, and a `WeatherType`. Produced by repository mappers from `WeatherDataDto`. |
| `WeatherInfo.kt` | Aggregated forecast snapshot exposed to the presentation layer. Contains hourly `WeatherData` entries keyed by forecast day index, plus the current hour's conditions. |
| `WeatherType.kt` | Sealed class of normalised weather conditions derived from WMO weather codes. Each variant holds a user-facing description and a Lottie animation resource ID. `WeatherType.fromWMO(code)` converts a raw API code into the correct type. |

**What goes here as the project grows:** repository interfaces, use cases.

---

## `presentation/`

Jetpack Compose UI layer. Observes domain state and renders it — no business logic lives here.

| File | Purpose |
|---|---|
| `MainActivity.kt` | Single-activity entry point. Sets up the Compose content root with `LekkerWeatherTheme`. |

### `presentation/ui/theme/`

Material 3 theming applied app-wide.

| File | Purpose |
|---|---|
| `Color.kt` | Color palette definitions. |
| `Theme.kt` | `LekkerWeatherTheme` composable — wires colors, typography, and shapes into a `MaterialTheme`. |
| `Type.kt` | Typography scale. |

**What goes here as the project grows:** screen composables, ViewModels, UI state classes, navigation setup, reusable components.

---

## `res/`

### `res/raw/`

Lottie animation JSON files (`anim_*.json`), one per weather condition. Referenced by `WeatherType` via `@RawRes` IDs so the UI layer can play them without knowing the file names directly.

### `res/drawable/`

Static vector icon fallbacks (`ic_*.xml`) for weather conditions and UI elements (wind, pressure, rain drop).

### `res/mipmap-*/`

Launcher icons at each screen density.
