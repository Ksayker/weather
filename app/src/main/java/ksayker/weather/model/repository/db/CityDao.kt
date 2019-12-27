package ksayker.weather.model.repository.db

import androidx.room.*
import ksayker.weather.model.entity.City


@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAll(): List<City>

    @Query("SELECT * FROM city WHERE id = :id")
    fun getById(id: Long): City?

    @Insert
    fun insert(city: City)

    @Update
    fun update(city: City)

    @Delete
    fun delete(city: City)

    @Query("SELECT COUNT(*) FROM city")
    fun getCount(): Int
}