package com.bdn.weathersample.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityInfo")
data class CityModel(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "latitude")  val lat: Double?,
    @ColumnInfo(name = "longitude")  val lon: Double?,
    @ColumnInfo(name = "name") var name: String? = null
)