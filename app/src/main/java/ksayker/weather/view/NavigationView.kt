package ksayker.weather.view

import ksayker.weather.model.entity.City

interface NavigationView {
    fun navigateToCity(city: City)
    fun navigateToAddCity()
}