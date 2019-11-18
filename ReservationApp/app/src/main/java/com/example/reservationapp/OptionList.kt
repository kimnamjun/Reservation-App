package com.example.reservationapp

import android.os.Parcel
import android.os.Parcelable

class OptionList(var optionname: String?) : Parcelable {
    //일단은 소포화 할필요없어졌는데 그냥 내비둠 마무리때도 필요없으면 parceable지우기
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(optionname)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OptionList> = object : Parcelable.Creator<OptionList> {
            override fun createFromParcel(source: Parcel): OptionList = OptionList(source)
            override fun newArray(size: Int): Array<OptionList?> = arrayOfNulls(size)
        }
    }
}