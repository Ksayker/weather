package ksayker.weather.model.repository

import android.content.Context
import androidx.room.EmptyResultSetException
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
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
    @Inject
    lateinit var networkService: NetworkService

    init {
        App.getApp()
            .componentManager
            .repositoryComponent
            .inject(this)
    }

    private fun saveCityToDb(city: City) {
        val cityDao = dataBase.cityDao()

        val observer = object : DisposableSingleObserver<City?>() {
            override fun onSuccess(t: City) {
                cityDao.update(city)
            }

            override fun onError(e: Throwable) {
                if (e is EmptyResultSetException) {
                    cityDao.insert(city)
                }
            }
        }

        cityDao.getById(city.id)
            .subscribe(observer)
    }

    private fun getWeatherFromDb(): Single<List<City>> {
        return dataBase.cityDao().getAll()
    }

    private fun getWeatherFromNetwork(placeId: Long): Single<City> {
        return networkService.weatherApi.getWeather(
            placeId,
            NetworkService.API_KEY,
            "metric"
        )
            .map { json: JsonObject ->
                val gsonBuilder = GsonBuilder()

                val deserializer = CityDeserializer()
                gsonBuilder.registerTypeAdapter(City::class.java, deserializer)

                val customGson = gsonBuilder.create()
                val city = customGson.fromJson(json, City::class.java)

                city
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
        return dataBase.cityDao().getCount()
    }
}