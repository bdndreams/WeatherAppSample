package com.bdn.weathersample.viewmodel

import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdn.weathersample.database.model.CityModel
import com.bdn.weathersample.network.Status
import com.bdn.weathersample.network.WeatherRepository
import com.bdn.weathersample.network.WeatherResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

fun View.setVisibility(isVisible: Boolean){
    if(isVisible) {
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}

object BindingAdapterClass{
    @BindingAdapter("bind:setProgressVisibility")
    @JvmStatic
    fun setProgressVisibility(view: RelativeLayout, isProgressHide: Boolean){
        if(isProgressHide){
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
        }
    }
}

class WeatherDetailViewModel(private val cityModel: CityModel?) : ViewModel(){

    val isProgressHide = MutableLiveData<Boolean>(false)
    val weatherData = MutableLiveData<WeatherResponse>()

    init {

        getWeatherInfo()
    }

    private fun getWeatherInfo(){
        cityModel?.let { cityModel ->

            viewModelScope.launch {
                with(WeatherRepository.WeatherRepositoryInstance) {
                    this?.let {

                        val map = HashMap<String, String>()
                        map["lat"] = cityModel.lat.toString()
                        map["lon"] = cityModel.lon.toString()
                        map["appid"] = "fae7190d7e6433ec3a45285ffcf55c86"
                        map["units"] = "metric"

                        networkCall(
                            "data/2.5/weather",
                            mapOf<String, String>(),
                            map
                        ).observeForever { networkResource ->
                            when(networkResource.status){
                                Status.LOADING->{
                                    isProgressHide.value = false
                                }
                                Status.SUCCESS->{
                                    isProgressHide.value = true
                                    val data: JsonObject? = networkResource.data
                                    Log.d("Bdn", "Success : ${networkResource.data}" )

                                    data?.let {
                                        try {
                                            val response = Gson().fromJson(
                                                data,
                                                WeatherResponse::class.java
                                            )
                                            weatherData.value = response
                                        }catch (e: Exception){
                                            Log.e("Bdn", "${e.toString()}")
                                        }

                                    }
                                }
                                Status.ERROR->{
                                    isProgressHide.value = true
                                    Log.d("Bdn", "Error : ${networkResource.data}" )
                                }
                            }
                            }
                    }
                }
            }
        }
    }
}