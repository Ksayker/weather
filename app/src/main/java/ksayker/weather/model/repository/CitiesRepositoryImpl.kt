package ksayker.weather.model.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import ksayker.weather.model.entity.City
import ksayker.weather.model.repository.rest.CityDeserializer
import ksayker.weather.model.repository.rest.NetworkService

class CitiesRepositoryImpl(val context: Context) : CitiesRepository {

    override fun getCities(constraint: String): Single<List<City>> {
        return NetworkService.getInstance().weatherApi.findCities(constraint, NetworkService.API_KEY)
            .firstElement()
            .flatMapSingle { json: JsonObject ->
                Single.fromCallable {
                    val gsonBuilder = GsonBuilder()

                    val deserializer = CityDeserializer()
                    gsonBuilder.registerTypeAdapter(City::class.java, deserializer)

                    val customGson = gsonBuilder.create()
                    val city = customGson.fromJson<List<City>>(json["list"], object : TypeToken<List<City>>() {}.type)

                    city
                }
            }
    }
}