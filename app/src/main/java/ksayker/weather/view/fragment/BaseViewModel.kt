package ksayker.weather.view.fragment

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}