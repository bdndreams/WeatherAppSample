package com.bdn.weathersample.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdn.weathersample.database.WeatherDatabase
import com.bdn.weathersample.database.model.CityModel
import com.bdn.weathersample.database.repositiory.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MapViewModel(val application: Application) : ViewModel(){

    var cityName:String = "Ahmedabad"
    var lat:Double = 23.022
    var lng: Double = 72.57
    private val cityRepository: CityRepository

    val savingState = MutableLiveData<SavedState>()
    val isSaveButtonEnabled = MutableLiveData<Boolean>(false)

    init {
        val cityDao = WeatherDatabase.getDatabase(application).cityDao()
        cityRepository = CityRepository(cityDao)
    }

    fun saveCity(){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                savingState.value = SavedState.Loading
                try {
                    cityRepository.insertCity(CityModel(lat = lat, lon = lng, name = cityName))
                    savingState.value = SavedState.Success
                }catch (e:Exception){
                    Log.d("MapVieModel", "$e")
                    savingState.value = SavedState.Error
                }
            }
        }
    }

    sealed class SavedState{
        object Loading : SavedState()
        object Success : SavedState()
        object Error: SavedState()
    }
}