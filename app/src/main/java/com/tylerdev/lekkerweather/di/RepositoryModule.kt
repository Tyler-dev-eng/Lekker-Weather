package com.tylerdev.lekkerweather.di

import com.tylerdev.lekkerweather.data.repository.WeatherRepositoryImpl
import com.tylerdev.lekkerweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that binds the domain [WeatherRepository] contract to its data-layer implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /** Supplies [WeatherRepositoryImpl] wherever [WeatherRepository] is injected. */
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}