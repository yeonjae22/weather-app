package com.yeonberry.weatherapp.data.repository

import com.yeonberry.weatherapp.data.model.LocationResponse
import com.yeonberry.weatherapp.data.model.SearchLocationResponse
import retrofit2.Response

interface WeatherRepository {
    suspend fun searchLocation(): Response<List<SearchLocationResponse>>

    suspend fun getLocationWeather(woeid: Int): Response<LocationResponse>
}