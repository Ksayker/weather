package ksayker.weather.model.repository

import io.reactivex.Single
import ksayker.weather.model.entity.City

interface CitiesRepository {

    fun getCities(constraint: String): Single<List<City>>
}