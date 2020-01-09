package ksayker.weather.model.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import ksayker.weather.App
import ksayker.weather.model.entity.City
import ksayker.weather.model.repository.rest.CityDeserializer
import ksayker.weather.model.repository.rest.NetworkService
import javax.inject.Inject

class CitiesRepositoryImpl(val context: Context) : CitiesRepository {

    @Inject
    lateinit var networkService: NetworkService

    init {
        App.getApp()
            .componentManager
            .repositoryComponent
            .inject(this)
    }

    override fun getCities(constraint: String): Single<List<City>> {
        return networkService.weatherApi.findCities(constraint, NetworkService.API_KEY)
            .map { json: JsonObject ->
                val gsonBuilder = GsonBuilder()

                val deserializer = CityDeserializer()
                gsonBuilder.registerTypeAdapter(City::class.java, deserializer)

                val customGson = gsonBuilder.create()
                val city = customGson.fromJson<List<City>>(json["list"], object : TypeToken<List<City>>() {}.type)

                city
            }
    }
}