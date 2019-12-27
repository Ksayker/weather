package ksayker.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ksayker.weather.PermissionManager
import ksayker.weather.R
import ksayker.weather.model.entity.City
import ksayker.weather.view.dialog.MessageDialogFragment
import ksayker.weather.view.fragment.AddCityFragment
import ksayker.weather.view.fragment.CitiesListFragment
import ksayker.weather.view.fragment.MapFragment


class MainActivity : AppCompatActivity(), NavigationView, AddCityFragment.OnAddCityListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCitiesListFragment()
    }

    override fun navigateToCity(city: City) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.citiesListHolder, MapFragment.newInstance(city))
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToAddCity() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.citiesListHolder, AddCityFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun initCitiesListFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.citiesListHolder)

        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.citiesListHolder, CitiesListFragment.newInstance(), CitiesListFragment.TAG)
                .commit()
        }
    }

    override fun addCity(city: City) {
        val fragment = supportFragmentManager.findFragmentByTag(CitiesListFragment.TAG)
        (fragment as? CitiesListFragment)?.addCity(city)
    }
}
