package com.bdn.weathersample.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponse(
    @SerializedName("weather") val weather: ArrayList<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("clouds") val clouds: Cloud,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("coord") val coordinate: Coordinate,
    @SerializedName("name") val name: String

) : Serializable

data class Coordinate(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
)

data class Weather(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
) : Serializable


data class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Double
) : Serializable

data class Sys(
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Double,
    @SerializedName("sunset") val sunset: Double
) : Serializable

data class Cloud(
    @SerializedName("all") val all: Double
) : Serializable

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("temp_min") val min: Double,
    @SerializedName("temp_max") val max: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("sea_level") val seaLevel: Double
) : Serializable