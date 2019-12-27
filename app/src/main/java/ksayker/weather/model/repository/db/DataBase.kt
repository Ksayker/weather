package ksayker.weather.model.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ksayker.weather.model.entity.City

@Database(entities = [City::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}