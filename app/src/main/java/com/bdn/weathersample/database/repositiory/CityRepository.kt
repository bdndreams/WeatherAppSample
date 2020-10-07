package com.bdn.weathersample.database.repositiory

import com.bdn.weathersample.database.model.CityModel
import core.database.CityDao

class CityRepository(private val cityDao: CityDao) {

    fun insertCity(city: CityModel):Long {
        return cityDao.insert(city)
    }

    fun deleteCity(city:CityModel):Int{
        return cityDao.delete(city)
    }

    fun getAllCities():List<CityModel>{
        return cityDao.getAllCities()
    }
}