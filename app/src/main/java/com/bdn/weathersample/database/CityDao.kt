package core.database

import androidx.room.*
import com.bdn.weathersample.database.model.CityModel

/**
 * It handles the database operation for User Info
 */
@Dao
abstract class CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(city: CityModel): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(city: CityModel): Int

    @Delete
    abstract fun delete(city: CityModel): Int

    @Query("SELECT * FROM cityinfo")
    abstract fun getAllCities(): List<CityModel>

}