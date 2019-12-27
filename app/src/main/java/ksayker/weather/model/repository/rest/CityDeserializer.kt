package ksayker.weather.model.repository.rest

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ksayker.weather.model.entity.City
import java.lang.reflect.Type

class CityDeserializer : JsonDeserializer<City> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): City {
        val city = City()
        if (json != null) {
            val j = json.asJsonObject

            city.id = j["id"].asLong
            city.name = j["name"].asString
            city.temperature = j["main"].asJsonObject["temp"].asInt
            city.country = j["sys"].asJsonObject["country"].asString
            city.latitude = j["coord"].asJsonObject["lat"].asDouble
            city.longitude = j["coord"].asJsonObject["lon"].asDouble
        }

        return city
    }
}