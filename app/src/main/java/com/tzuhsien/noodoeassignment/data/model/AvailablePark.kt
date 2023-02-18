package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class AvailablePark(
    @SerializedName("ChargeStation") val chargeStation: ChargeStation?,
//    val availablebus: Int,
    val availablecar: Int,
//    val availablemotor: Int,
    val id: String
)