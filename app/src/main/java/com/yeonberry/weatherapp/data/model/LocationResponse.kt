package com.yeonberry.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("title")
    val title: String?,
    @SerializedName("consolidated_weather")
    val weather: List<Weather>?
)

data class Weather(
    @SerializedName("weather_state_name")
    val weatherStateName: String?,
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String?,
    @SerializedName("the_temp")
    val theTemp: Double?,
    @SerializedName("humidity")
    val humidity: Int?
)

data class LocationWeather(
    val title: String?,
    val today: Weather?,
    val tomorrow: Weather?
)