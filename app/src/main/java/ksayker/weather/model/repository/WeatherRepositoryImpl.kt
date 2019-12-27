package ksayker.weather.model.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import ksayker.weather.App
import ksayker.weather.model.entity.City
import ksayker.weather.model.repository.db.DataBase
import ksayker.weather.model.repository.rest.CityDeserializer
import ksayker.weather.model.repository.rest.NetworkService
import ksayker.weather.utils.NetworkUtils
import javax.inject.Inject

class WeatherRepositoryImpl(val context: Context) : WeatherRepository {
    @Inject
    lateinit var dataBase: DataBase

    init {
        App.getApp()
            .componentManager
            .repositoryComponent
            .inject(this)
    }

    private fun saveCityToDb(city: City) {
        val cityDao = dataBase.cityDao()

        if (cityDao.getById(city.id) == null) {
            cityDao.insert(city)
        } else {
            cityDao.update(city)
        }
    }

    private fun getWeatherFromDb(): Single<List<City>> {
        return Single.fromCallable {
            dataBase.cityDao().getAll()
        }
    }

    private fun getWeatherFromNetwork(placeId: Long): Single<City> {
        return NetworkService.getInstance().weatherApi.getWeather(
            placeId,
            NetworkService.API_KEY,
            "metric"
        )
            .firstElement()
            .flatMapSingle { json: JsonObject ->
                Single.fromCallable {
                    val gsonBuilder = GsonBuilder()

                    val deserializer = CityDeserializer()
                    gsonBuilder.registerTypeAdapter(City::class.java, deserializer)

                    val customGson = gsonBuilder.create()
                    val city = customGson.fromJson(json, City::class.java)

                    city
                }
            }
            .doOnSuccess { saveCityToDb(it) }
    }

    override fun getWeather(): Single<List<City>> {
        return if (NetworkUtils.isInternetAvailable()) {
            getWeatherFromDb()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { getWeatherFromNetwork(it.id).toObservable() }
                .toList()
        } else {
            getWeatherFromDb()
        }
    }

    override fun getWeather(id: Long): Single<City> {
        return getWeatherFromNetwork(id)
    }

    override fun getDataBaseCitiesCount(): Single<Int> {
        return Single.fromCallable {
            dataBase.cityDao().getCount()
        }
    }
}