package ksayker.weather

import android.app.Application
import ksayker.weather.di.ComponentManager


class App : Application() {
    lateinit var componentManager: ComponentManager
        private set

    override fun onCreate() {
        instance = this
        componentManager = ComponentManager(this)

        LocationManager.instance?.init(applicationContext)

        super.onCreate()
    }

    companion object {
        private lateinit var instance: App

        fun getApp() = instance
    }
}