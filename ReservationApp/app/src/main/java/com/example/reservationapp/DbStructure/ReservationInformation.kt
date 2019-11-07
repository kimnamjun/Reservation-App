package com.example.reservationapp

class ReservationInformation (val restaurantName : String, val userID : String, val reservationTime : String, val waitingNumber : Int){
    override fun toString(): String {
        return "$restaurantName / $userID / $reservationTime / $waitingNumber"
    }
}