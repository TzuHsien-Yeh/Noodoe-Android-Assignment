package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class Park(
    @SerializedName("ChargingStation") val chargingStation: String?,
    val address: String,
    val area: String,
    val id: String,
    val name: String,
    val serviceTime: String,
    val tel: String,
    @SerializedName("totalcar") val totalCar: Int
)