package ksayker.weather.view.adapter

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ksayker.weather.R
import ksayker.weather.databinding.ItemCityBinding
import ksayker.weather.model.entity.City

open class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CityHolder>() {

    private val items = ArrayList<City>()
    private var nearestCity = City.NONE
    private var location: Location? = null

    lateinit var cityClickListener: OnCityClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = DataBindingUtil.inflate<ItemCityBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_city,
            parent,
            false
        )

        return CityHolder(binding.root) {
            binding.isSelected = it == nearestCity && it != City.NONE
            binding.city = it
            binding.cityClickListener = cityClickListener
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addCity(city: City) {
        if (city != City.NONE) {
            val index = items.indexOf(city)

            if (index >= 0) {
                findNearestCity()
                notifyItemChanged(index)
            } else {
                items.add(city)
                findNearestCity()
                notifyItemInserted(items.size - 1)
            }
        }
    }

    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addAllCities(cities: List<City>) {
        val start = items.size
        items.addAll(cities)

        findNearestCity()
        notifyItemRangeInserted(start, items.size - 1)
    }

    fun setLocation(location: Location?) {
        this.location = location

        findNearestCity()
        notifyDataSetChanged()
    }

    private fun findNearestCity() {
        val location = location

        if (location == null) {
            nearestCity = City.NONE
        } else {
            val distances = ArrayList<Float>(items.size)
            items.forEach {
                distances.add(location.distanceTo(Location("").apply {
                    latitude = it.latitude
                    longitude = it.longitude
                }))
            }

            val index = distances.withIndex().minBy { (_, f) -> f }?.index
            if (index != null) {
                nearestCity = items[index]
            }
        }
    }

    inner class CityHolder(itemView: View, private val binder: (city: City) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(city: City) {
            binder.invoke(city)
        }
    }

    interface OnCityClickListener {
        fun onCityClicked(city: City)
    }
}