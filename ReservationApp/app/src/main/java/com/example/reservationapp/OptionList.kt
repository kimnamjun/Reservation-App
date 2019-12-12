package com.example.reservationapp

import android.os.Parcel
import android.os.Parcelable
//여기는 메뉴에대한 data클래스입니다 다른 액티비티에서 OptionList를 생성할수 있도록 parcelable했습니다.
class OptionList(var optionname: String?, var Callnumber: Int = 0) : Parcelable{
    constructor(source: Parcel) : this(
    source.readString(),
    source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(optionname)
        writeInt(Callnumber)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OptionList> = object : Parcelable.Creator<OptionList> {
            override fun createFromParcel(source: Parcel): OptionList = OptionList(source)
            override fun newArray(size: Int): Array<OptionList?> = arrayOfNulls(size)
        }
    }
}