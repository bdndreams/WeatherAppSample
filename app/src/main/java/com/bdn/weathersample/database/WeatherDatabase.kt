package com.bdn.weathersample.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bdn.weathersample.database.model.CityModel
import core.database.CityDao


@Database(
    entities = [CityModel::class],
    version = 1,
    exportSchema = true
)

abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(
            application: Application
        ): WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    WeatherDatabase::class.java,
                    "weather_db"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
