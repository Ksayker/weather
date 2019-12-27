package ksayker.weather.model.repository.rest

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("data/2.5/weather")
    fun getWeather(@Query("id") id: Long, @Query("appid") appId: String, @Query("units") units: String): Observable<JsonObject>

    @GET("data/2.5/find")
    fun findCities(@Query("q") query: String, @Query("appid") appId: String): Observable<JsonObject>
}