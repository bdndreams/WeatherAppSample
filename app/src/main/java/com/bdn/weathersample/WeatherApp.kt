package com.bdn.weathersample

import android.app.Application
import com.bdn.weathersample.network.WeatherRepository

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()

        WeatherRepository.WeatherRepositoryInstance?.initBaseRepository(this)
    }
}