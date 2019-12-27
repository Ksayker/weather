package ksayker.weather.utils

import android.content.Context
import ksayker.weather.R
import ksayker.weather.model.entity.City

object CityUtils {

    fun formatTemperature(context: Context, city: City): String {
        return context.getString(
            if (city.temperature > 0) R.string.itemCity_positiveTemperature else R.string.itemCity_temperature,
            city.temperature)
    }
}