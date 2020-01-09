package ksayker.weather.model.repository.db

import androidx.room.*
import io.reactivex.Single
import ksayker.weather.model.entity.City


@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAll(): Single<List<City>>

    @Query("SELECT * FROM city WHERE id = :id")
    fun getById(id: Long): Single<City?>

    @Insert
    fun insert(city: City)

    @Update
    fun update(city: City)

    @Delete
    fun delete(city: City)

    @Query("SELECT COUNT(*) FROM city")
    fun getCount(): Single<Int>
}