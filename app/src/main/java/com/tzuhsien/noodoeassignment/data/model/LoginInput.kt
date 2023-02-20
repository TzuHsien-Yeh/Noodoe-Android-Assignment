package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class LoginInput (
    @SerializedName("username") val userName: String,
    val password: String
    )