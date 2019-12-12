package com.example.reservationapp

import android.os.Parcel
import android.os.Parcelable


class basketlist(var foodname: String, var option: String, var callnumber: Int, var price: Int, var foodphoto: String) :    Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString(),
        source.readInt(),
        source.readInt(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(foodname)
        writeString(option)
        writeInt(callnumber)
        writeInt(price)
        writeString(foodphoto)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<basketlist> = object : Parcelable.Creator<basketlist> {
            override fun createFromParcel(source: Parcel): basketlist = basketlist(source)
            override fun newArray(size: Int): Array<basketlist?> = arrayOfNulls(size)
        }
    }
}