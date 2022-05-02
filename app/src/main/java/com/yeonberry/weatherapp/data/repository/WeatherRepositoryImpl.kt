package com.yeonberry.weatherapp.data.repository

import com.yeonberry.weatherapp.api.WeatherService
import com.yeonberry.weatherapp.data.model.LocationResponse
import com.yeonberry.weatherapp.data.model.SearchLocationResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherService) :
    WeatherRepository {
    override suspend fun searchLocation(): Response<List<SearchLocationResponse>> {
        return api.searchLocation()
    }

    override suspend fun getLocationWeather(woeid: Int): Response<LocationResponse> {
        return api.getLocationWeather(woeid)
    }
}