package ksayker.weather.model.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class City() : Parcelable {
    @PrimaryKey
    var id = Long.MIN_VALUE
    var name: String = ""
    var temperature = Int.MIN_VALUE
    var country = ""
    var latitude: Double = Double.MIN_VALUE
    var longitude: Double = Double.MIN_VALUE

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString() ?: ""
        temperature = parcel.readInt()
        country = parcel.readString() ?: ""
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(name)
        dest?.writeInt(temperature)
        dest?.writeString(country)
        dest?.writeDouble(latitude)
        dest?.writeDouble(longitude)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<City> {
        val NONE = City()

        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}