package chriscorptechnologies.learning.kotlin.notepad

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by Christophe KINDJI on 18/01/2018.
 */
data class Note(var title: String = "",
                var text: String = "",
                var fileName: String = "") : Parcelable, Serializable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        private val serialVersionUid: Long = 4242424242
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }


}