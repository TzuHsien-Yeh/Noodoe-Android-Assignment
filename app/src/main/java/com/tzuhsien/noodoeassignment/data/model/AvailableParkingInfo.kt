package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class AvailableParkingInfo(
    @SerializedName("UPDATETIME") val updateTime: String,
    val park: List<AvailablePark>
)