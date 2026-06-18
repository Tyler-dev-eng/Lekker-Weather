package com.tylerdev.lekkerweather.di

import com.tylerdev.lekkerweather.data.location.DefaultLocationTracker
import com.tylerdev.lekkerweather.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that binds the domain [LocationTracker] contract to its data-layer implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    /** Supplies [DefaultLocationTracker] wherever [LocationTracker] is injected. */
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}