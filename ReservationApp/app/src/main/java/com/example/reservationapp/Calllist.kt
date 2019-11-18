package com.example.reservationapp

import android.os.Parcel
import android.os.Parcelable


class Calllist(var foodname: String, var price: String) : Parcelable {
    //class를 재정의 했을때 생성자 종류라도 바꿨을때는 다시 다지우고 parcelable generate해줘야한다 근데 아무리 지우고 다시 해도 안된다
    //위에 import부분도 지우고 다시 받아야함! 즉 아예다시! import도 지우고 다시 generat받기!
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(foodname)
        writeString(price)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Calllist> = object : Parcelable.Creator<Calllist> {
            override fun createFromParcel(source: Parcel): Calllist = Calllist(source)
            override fun newArray(size: Int): Array<Calllist?> = arrayOfNulls(size)
        }
    }
}

