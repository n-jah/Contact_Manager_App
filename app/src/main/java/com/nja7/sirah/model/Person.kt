package com.nja7.sirah.model

import android.os.Parcel
import android.os.Parcelable

data class Person(var name: String?, var number: String?, var isChecked: Boolean = false) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    constructor() : this("", "", false)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(number)
        dest.writeByte(if (isChecked) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
