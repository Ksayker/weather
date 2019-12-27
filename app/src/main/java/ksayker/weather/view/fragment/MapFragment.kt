package ksayker.weather.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ksayker.weather.R
import ksayker.weather.databinding.FragmentMapBinding
import ksayker.weather.model.entity.City
import ksayker.weather.utils.CityUtils

class MapFragment : BackPressFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var city: City

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        initData()
        initView(binding)

        binding.mapView.onCreate(savedInstanceState)

        return binding.root
    }

    private fun initData() {
        arguments?.getParcelable<City>(CITY_ARG)?.let {
            city = it
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map

        showCity(city)
    }

    private fun showCity(city: City) {
        val map = map
        val context = context

        if (map != null && context != null) {
            val coordinate = LatLng(city.latitude, city.longitude)

            val marker = MarkerOptions()
                .position(coordinate)
                .title(city.name)
                .snippet(CityUtils.formatTemperature(context, city))

            map.addMarker(marker)
                .showInfoWindow()

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 9.0f))
        }
    }

    private fun initView(binding: FragmentMapBinding) {
        initToolbar(binding.toolbar)

        binding.mapView.getMapAsync(this)
    }

    companion object {
        private const val CITY_ARG = "CITY_ARG"

        fun newInstance(city: City): Fragment {
            val bundle = Bundle()
            val fragment = MapFragment()

            bundle.putParcelable(CITY_ARG, city)
            fragment.arguments = bundle

            return fragment
        }
    }
}