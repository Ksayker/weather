package ksayker.weather.model.repository

import io.reactivex.Single
import ksayker.weather.model.entity.City

interface WeatherRepository {

    fun getWeather(): Single<List<City>>

    fun getWeather(id: Long): Single<City>

    fun getDataBaseCitiesCount() : Single<Int>
}