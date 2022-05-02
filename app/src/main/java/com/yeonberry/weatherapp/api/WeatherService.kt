package com.yeonberry.weatherapp.api

import com.yeonberry.weatherapp.data.model.LocationResponse
import com.yeonberry.weatherapp.data.model.SearchLocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("/api/location/search")
    suspend fun searchLocation(
        @Query("query") query: String? = "se"
    ): Response<List<SearchLocationResponse>>

    @GET("/api/location/{woeid}")
    suspend fun getLocationWeather(
        @Path("woeid") woeid: Int
    ): Response<LocationResponse>
}