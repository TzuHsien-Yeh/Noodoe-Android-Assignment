package com.tzuhsien.noodoeassignment.data.model

data class ParkingInfoToDisplay (
    val id: String,
    val name: String,
    val address: String,
    val totalCar: Int,
    val availableCar: Int,
    val socketQty: Int?,
    val socketInUse: Int?,
    val socketAvailable: Int?
    )