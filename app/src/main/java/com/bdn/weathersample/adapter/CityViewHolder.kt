package com.bdn.weathersample.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bdn.weathersample.database.model.CityModel
import com.bdn.weathersample.databinding.ItemCityBinding
import com.bdn.weathersample.viewmodel.HomeViewModel

class CityViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    private val binding = ItemCityBinding.bind(parent)

    fun bindTo(item: CityModel, viewModel: HomeViewModel) {
        binding.items = item
        binding.homeViewModel = viewModel
    }
}