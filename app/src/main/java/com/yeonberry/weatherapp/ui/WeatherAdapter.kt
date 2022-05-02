package com.yeonberry.weatherapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeonberry.weatherapp.R
import com.yeonberry.weatherapp.data.model.LocationWeather
import com.yeonberry.weatherapp.databinding.ItemTitleBinding
import com.yeonberry.weatherapp.databinding.ItemWeatherBinding

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = mutableListOf<LocationWeather>()

    @SuppressLint("NotifyDataSetChanged")
    fun addAllItem(items: List<LocationWeather>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_title -> {
                TitleViewHolder(
                    ItemTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                WeatherViewHolder(
                    ItemWeatherBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeatherViewHolder) {
            holder.bind(itemList[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_title
            else -> R.layout.item_weather
        }
    }

    override fun getItemCount(): Int = itemList.size + 1
}

class TitleViewHolder(binding: ItemTitleBinding) : RecyclerView.ViewHolder(binding.root)

class WeatherViewHolder(private val binding: ItemWeatherBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LocationWeather) {
        binding.apply {
            tvLocation.text = item.title
            Glide.with(binding.ivTodayIcon)
                .load(getResourceId(ivTodayIcon.context, item.today?.weatherStateAbbr))
                .into(binding.ivTodayIcon)
            tvTodayName.text = item.today?.weatherStateName
            tvTodayTemp.text = String.format(
                tvTodayTemp.context.getString(R.string.format_temp),
                item.today?.theTemp
            )
            tvTodayHumidity.text = String.format(
                tvTodayHumidity.context.getString(R.string.format_humidity),
                item.today?.humidity
            )
            Glide.with(binding.ivTomorrowIcon)
                .load(getResourceId(ivTomorrowIcon.context, item.today?.weatherStateAbbr))
                .into(binding.ivTomorrowIcon)
            tvTomorrowName.text = item.tomorrow?.weatherStateName
            tvTomorrowTemp.text = String.format(
                tvTomorrowTemp.context.getString(R.string.format_temp),
                item.tomorrow?.theTemp
            )
            tvTomorrowHumidity.text = String.format(
                tvTomorrowHumidity.context.getString(R.string.format_humidity),
                item.tomorrow?.humidity
            )
        }
    }

    private fun getResourceId(context: Context, name: String?): Int {
        return context.resources.getIdentifier(
            "ic_${name}",
            "drawable",
            context.packageName
        )
    }
}