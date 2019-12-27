package ksayker.weather.view.fragment

import android.location.Location
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ksayker.weather.App
import ksayker.weather.model.entity.City
import ksayker.weather.model.repository.WeatherRepository
import ksayker.weather.utils.NetworkUtils
import ksayker.weather.view.helper.SingleLiveEvent
import ksayker.weather.view.adapter.CitiesAdapter
import ksayker.weather.view.helper.MessageFactory
import javax.inject.Inject

class CitiesListViewModel: ViewModel(), CitiesAdapter.OnCityClickListener {
    private lateinit var messageFactory: MessageFactory
    private lateinit var citiesAdapter: CitiesAdapter

    private var clickCityObserver: SingleLiveEvent<City> = SingleLiveEvent()
    private var clickAddCityObserver: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private var messageObserver: SingleLiveEvent<String> = SingleLiveEvent()

    private var initialized = false

    @Inject
    lateinit var repository: WeatherRepository

    private fun loadWeather(id: Long) {
        val observer = object : DisposableSingleObserver<City>() {
            override fun onSuccess(city: City) {
                citiesAdapter.addCity(city)
            }

            override fun onError(e: Throwable) {
                messageObserver.value = messageFactory.getCityLadingErrorMessage(id)
            }
        }

        repository.getWeather(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadWeather() {
        repository.getWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<City>>() {
                override fun onSuccess(cities: List<City>) {
                    citiesAdapter.addAllCities(cities)
                }

                override fun onError(e: Throwable) {
                    messageObserver.value = messageFactory.getListLoadingErrorMessage()
                }
            })
    }

    private fun initWeatherData() {
        repository.getDataBaseCitiesCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Int>() {
                override fun onSuccess(count: Int) {
                    if (count == 0) {
                        loadWeather(KIEV_ID)
                        loadWeather(DNIPROPETROVSK_ID)
                    } else {
                        loadWeather()
                    }
                }

                override fun onError(e: Throwable) {
                    messageObserver.value = messageFactory.getInitializeDataBaseErrorMessage()
                }
            })
    }

    fun init(messageFactory: MessageFactory) {
        if (!initialized) {
            App.getApp()
                .componentManager
                .repositoryComponent
                .inject(this)

            this.messageFactory = messageFactory
            citiesAdapter = CitiesAdapter()
            citiesAdapter.cityClickListener = this

            initWeatherData()

            initialized = true
        }
    }

    fun getCitiesAdapter() = citiesAdapter

    fun getClickCityObserver() = clickCityObserver

    fun getClickAddCityObserver() = clickAddCityObserver

    fun getMessageObserver() = messageObserver

    fun addCityClick() {
        if (NetworkUtils.isInternetAvailable()) {
            clickAddCityObserver.value = true
        } else {
            messageObserver.value = messageFactory.getCheckInternetConnectionMessage()
        }
    }

    override fun onCityClicked(city: City) {
        if (NetworkUtils.isInternetAvailable()) {
            clickCityObserver.value = city
        } else {
            messageObserver.value = messageFactory.getCheckInternetConnectionMessage()
        }
    }

    fun addCity(city: City) {
        loadWeather(city.id)
    }

    fun restoreMessage() {
        messageObserver.value = ""
    }

    fun showMessage(message: String) {
        messageObserver.value = message
    }

    fun showCurrentLocation(location: Location?) {
        citiesAdapter.setLocation(location)
    }

    companion object {
        private const val KIEV_ID: Long = 709930
        private const val DNIPROPETROVSK_ID: Long = 703448
    }
}