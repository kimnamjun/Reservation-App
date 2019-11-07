package com.example.reservationapp.DbStructure

class UserInfo (userID : String){
    var userID : String = userID
    var resName : String? = ""

    constructor(userID : String, resName : String) : this(userID){
        this.userID = userID
        this.resName = resName
    }
}