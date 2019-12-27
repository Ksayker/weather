package ksayker.weather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location

class LocationManager {
    private var androidLocationManager: android.location.LocationManager? = null
    private var provider: String = android.location.LocationManager.PASSIVE_PROVIDER
    private var context: Context? = null

    val lastLocation: Location?
        @SuppressLint("MissingPermission")
        get() {
            return androidLocationManager?.getLastKnownLocation(provider)
        }

    fun init(context: Context) {
        this.context = context
        androidLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager?
    }

    companion object {
        private var sInstance: LocationManager? = null

        val instance: LocationManager?
            get() {
                if (sInstance == null) {
                    sInstance = LocationManager()
                }

                return sInstance
            }
    }
}