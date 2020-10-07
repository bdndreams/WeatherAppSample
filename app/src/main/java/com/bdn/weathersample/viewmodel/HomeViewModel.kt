package com.bdn.weathersample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdn.weathersample.database.WeatherDatabase
import com.bdn.weathersample.database.model.CityModel
import com.bdn.weathersample.database.repositiory.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val application: Application): ViewModel() {
    var title: String = "Weather App"

    private val cityRepository: CityRepository
    var cityList = MutableLiveData<List<CityModel>>(ArrayList())
    val cityModelLiveData = MutableLiveData<CityModel>()

    init {
        val cityDao = WeatherDatabase.getDatabase(application).cityDao()
        cityRepository = CityRepository(cityDao)
    }

    fun loadBookmarkCities(){
        viewModelScope.launch(Dispatchers.Main){
            cityList.value = cityRepository.getAllCities()
        }
    }

    public fun onCityItemClickListener(item: CityModel) {
        cityModelLiveData.value = item
    }

    public fun onDeleteItemClickListener(item: CityModel){
        viewModelScope.launch(Dispatchers.Main) {
            cityRepository.deleteCity(item)
            cityList.value?.let {
                (it as ArrayList).remove(item)
                cityList.value = it
            }
        }
    }
}