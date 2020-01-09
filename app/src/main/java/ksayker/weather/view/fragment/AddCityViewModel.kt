package ksayker.weather.view.fragment

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ksayker.weather.App
import ksayker.weather.model.entity.City
import ksayker.weather.model.repository.CitiesRepository
import ksayker.weather.view.adapter.SingleSelectionCityAdapter
import ksayker.weather.view.helper.MessageFactory
import ksayker.weather.view.helper.SingleLiveEvent
import javax.inject.Inject


class AddCityViewModel: BaseViewModel() {
    private lateinit var citiesAdapter: SingleSelectionCityAdapter

    private var initialized = false

    @Inject
    lateinit var citiesRepository: CitiesRepository
    @Inject
    lateinit var messageFactory: MessageFactory

    lateinit var callback: AddCityViewModelCallback

    private var messageObserver: SingleLiveEvent<String> = SingleLiveEvent()

    fun init() {
        if (!initialized) {
            App.getApp()
                .componentManager
                .repositoryComponent
                .inject(this)

            citiesAdapter = SingleSelectionCityAdapter()

            initialized = true
        }
    }

    fun addCity() {
        callback.addCity(citiesAdapter.selectedCity)
    }

    fun cancel() {
        callback.cancel()
    }

    fun getCitiesAdapter() = citiesAdapter

    fun getMessageObserver() = messageObserver

    fun afterTextChanged(s: Editable?) {
        citiesAdapter.clear()

        if (s?.length ?:0 >= 3) {
            val observer = object : DisposableSingleObserver<List<City>>() {
                override fun onSuccess(cities: List<City>) {
                    cities.forEach {
                        citiesAdapter.addCity(it)
                    }
                }

                override fun onError(e: Throwable) {
                    messageObserver.value = messageFactory.getCheckInternetConnectionMessage()
                }
            }

            citiesRepository.getCities(s.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

            addDisposable(observer)
        }
    }

    fun restoreMessage() {
        messageObserver.value = ""
    }

    interface AddCityViewModelCallback {
        fun addCity(city: City)

        fun cancel()
    }
}