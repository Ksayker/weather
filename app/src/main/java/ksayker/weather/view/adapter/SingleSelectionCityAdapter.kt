package ksayker.weather.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ksayker.weather.R
import ksayker.weather.databinding.ItemSelectableCityBinding
import ksayker.weather.model.entity.City

class SingleSelectionCityAdapter : CitiesAdapter(), CitiesAdapter.OnCityClickListener {
    var selectedCity: City = City.NONE
        private set

    init {
        cityClickListener = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = DataBindingUtil.inflate<ItemSelectableCityBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_selectable_city,
            parent,
            false
        )

        return CityHolder(binding.root) {
            binding.city = it
            binding.cityClickListener = cityClickListener
            binding.cbSelected.isChecked = it == selectedCity
        }
    }

    override fun onCityClicked(city: City) {
        selectedCity = if (city == selectedCity) {
            City.NONE
        } else {
            city
        }

        notifyDataSetChanged()
    }

    override fun clear() {
        super.clear()
        selectedCity = City.NONE
    }
}