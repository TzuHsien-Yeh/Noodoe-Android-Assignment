package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class AvailablePark(
    val id: String,
    @SerializedName("ChargeStation") val chargeStation: ChargeStation?,
    @SerializedName("availablecar") val availableCar: Int
//    val availablebus: Int,
//    val availablemotor: Int,
)