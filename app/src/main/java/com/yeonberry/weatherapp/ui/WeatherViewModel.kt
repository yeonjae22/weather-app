package com.yeonberry.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonberry.weatherapp.data.model.LocationWeather
import com.yeonberry.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _locationWeatherList = MutableLiveData<List<LocationWeather>>()
    val locationWeatherList: LiveData<List<LocationWeather>> get() = _locationWeatherList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorMessage.value = throwable.localizedMessage
        _isLoading.value = false
    }

    fun setIsLoading() {
        _isLoading.value = true
    }

    fun searchLocation() {
        viewModelScope.launch(exceptionHandler) {
            val response = repository.searchLocation()
            if (response.isSuccessful) {
                val responses = response.body()?.map { location ->
                    async {
                        location.woeid?.let { repository.getLocationWeather(it) }
                    }
                }?.awaitAll()

                val locationWeatherList = arrayListOf<LocationWeather>()
                responses?.forEach {
                    if (it?.isSuccessful == true) {
                        locationWeatherList.add(
                            LocationWeather(
                                it.body()?.title,
                                it.body()?.weather?.get(0),
                                it.body()?.weather?.get(1)
                            )
                        )
                    } else {
                        _errorMessage.value = response.code().toString()
                    }
                }
                _locationWeatherList.value = locationWeatherList

            } else {
                _errorMessage.value = response.code().toString()
            }
            _isLoading.value = false
        }
    }
}