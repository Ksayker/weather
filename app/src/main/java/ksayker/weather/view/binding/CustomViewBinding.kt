package ksayker.weather.view.binding

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ksayker.weather.R
import ksayker.weather.model.entity.City
import ksayker.weather.utils.CityUtils

@BindingAdapter("setAdapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = adapter
}

@BindingAdapter("setCityTemperature")
fun setCityTemperature(textView: TextView, city: City) {
    textView.text = CityUtils.formatTemperature(textView.context, city)
}

@BindingAdapter("setDetailCityInfo")
fun setDetailCityInfo(textView: TextView, city: City) {
    val text = "${city.name} ${city.country}"
    textView.text = text
}

@BindingAdapter("selectItem")
fun selectItem(view: View, isSelected: Boolean) {
    val color = if (isSelected) {
        ContextCompat.getColor(view.context, R.color.selectedItemBackground)
    } else {
        ContextCompat.getColor(view.context, android.R.color.transparent)
    }

    view.setBackgroundColor(color)
}