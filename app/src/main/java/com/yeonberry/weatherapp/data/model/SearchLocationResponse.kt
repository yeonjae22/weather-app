package com.yeonberry.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class SearchLocationResponse(
    @SerializedName("woeid")
    val woeid: Int?
)