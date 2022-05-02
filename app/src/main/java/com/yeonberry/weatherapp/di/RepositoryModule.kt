package com.yeonberry.weatherapp.di

import com.yeonberry.weatherapp.api.WeatherService
import com.yeonberry.weatherapp.data.repository.WeatherRepository
import com.yeonberry.weatherapp.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(
        weatherService: WeatherService
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherService
        )
    }
}