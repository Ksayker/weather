package ksayker.weather.model.repository.rest

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    private val retrofit: Retrofit

    val weatherApi: WeatherApi

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org"

        const val API_KEY = "d4aba712a7867b9c0812d955424bd5f6"
    }
}