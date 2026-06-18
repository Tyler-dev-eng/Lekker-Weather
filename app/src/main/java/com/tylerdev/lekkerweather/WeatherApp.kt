package com.tylerdev.lekkerweather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point that bootstraps Hilt dependency injection for the whole app.
 *
 * Registered in the manifest; Hilt generates the required component graph from modules in [com.tylerdev.lekkerweather.di].
 */
@HiltAndroidApp
class WeatherApp: Application()