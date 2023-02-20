package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class ChargeStation(
    @SerializedName("scoketStatusList") val socketStatusList: List<SocketStatus>
)