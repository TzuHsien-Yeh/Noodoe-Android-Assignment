package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class SocketStatus(
    @SerializedName("spot_abrv") val spotAbrv: String,
    @SerializedName("spot_status") val spotStatus: String
)