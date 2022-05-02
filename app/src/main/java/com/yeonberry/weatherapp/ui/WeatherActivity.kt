package com.yeonberry.weatherapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.yeonberry.weatherapp.R
import com.yeonberry.weatherapp.databinding.ActivityWeatherBinding
import com.yeonberry.weatherapp.util.DividerItemDecoration
import com.yeonberry.weatherapp.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter = WeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        setRefresh()
        showLocationWeatherList()
        showError()
        setLoadingIndicator()
        viewModel.setIsLoading()
        viewModel.searchLocation()
    }

    private fun initAdapter() {
        binding.rvWeather.apply {
            adapter = weatherAdapter
            addItemDecoration(
                DividerItemDecoration(
                    ContextCompat.getColor(
                        this@WeatherActivity,
                        R.color.gray
                    ), dpToPx(this@WeatherActivity, 1)
                )
            )
        }
    }

    private fun setRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.searchLocation()
            binding.rvWeather.isVisible = false
        }
    }

    private fun showLocationWeatherList() {
        viewModel.locationWeatherList.observe(this) {
            binding.rvWeather.isVisible = true
            binding.swipeRefresh.isRefreshing = false
            weatherAdapter.addAllItem(it)
        }
    }

    private fun showError() {
        viewModel.errorMessage.observe(this) {
            binding.swipeRefresh.isRefreshing = false
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLoadingIndicator() {
        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
    }
}