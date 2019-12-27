package ksayker.weather.view.helper

import android.content.Context
import ksayker.weather.R

class MessageFactory(val context: Context) {

    fun getCheckInternetConnectionMessage() =
        context.getString(R.string.listCities_checkInternetConnectionMessage)

    fun getInitializeDataBaseErrorMessage() =
        context.getString(R.string.listCities_initializeDataBaseErrorMessage)

    fun getListLoadingErrorMessage() =
        context.getString(R.string.listCities_listLadingErrorMessage)

    fun getCityLadingErrorMessage(id: Long) =
        context.getString(R.string.listCities_cityLadingErrorMessage, id)
}