package com.example.reservationapp

import android.os.Parcel
import android.os.Parcelable

//여기는 메뉴에대한 data클래스입니다 다른 액티비티에서 calllist를 생성할수 있도록 parcelable했습니다.
class Calllist(var foodname: String, var price: String, var foodphoto: String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(foodname)
        writeString(price)
        writeString(foodphoto)
    }

    fun getInfo() : String{
        return foodname + "," + price + "," + foodphoto
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Calllist> = object : Parcelable.Creator<Calllist> {
            override fun createFromParcel(source: Parcel): Calllist = Calllist(source)
            override fun newArray(size: Int): Array<Calllist?> = arrayOfNulls(size)
        }
    }
}

