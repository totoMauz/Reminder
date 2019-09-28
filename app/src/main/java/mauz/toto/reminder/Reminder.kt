package mauz.toto.reminder

import android.os.Parcel
import android.os.Parcelable

data class Reminder(val name: String, val duration: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    )

    override fun toString(): String {
        return "$name\t${getDuration()}"
    }

    fun getDuration(): String {
        var hours = 0

        var minutes: Int = duration
        if (minutes >= TemplateActivity.HOUR_TO_MINUTE) {
            hours = (minutes / TemplateActivity.HOUR_TO_MINUTE)
            minutes -= hours * TemplateActivity.HOUR_TO_MINUTE
        }
        return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reminder> {
        override fun createFromParcel(parcel: Parcel): Reminder {
            return Reminder(parcel)
        }

        override fun newArray(size: Int): Array<Reminder?> {
            return arrayOfNulls(size)
        }
    }
}