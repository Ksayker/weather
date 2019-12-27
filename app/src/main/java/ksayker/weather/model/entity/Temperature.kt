package ksayker.weather.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Temperature() : Parcelable {
    @SerializedName("temp")
    var temperature = Int.MIN_VALUE

    constructor(parcel: Parcel) : this() {
        temperature = parcel.readInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Temperature

        if (temperature != other.temperature) return false

        return true
    }

    override fun hashCode(): Int {
        return temperature
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(temperature)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Temperature> {
        val NONE = Temperature()

        override fun createFromParcel(parcel: Parcel): Temperature {
            return Temperature(parcel)
        }

        override fun newArray(size: Int): Array<Temperature?> {
            return arrayOfNulls(size)
        }
    }
}