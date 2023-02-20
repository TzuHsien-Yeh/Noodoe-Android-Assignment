package com.tzuhsien.noodoeassignment.data.model

import com.google.gson.annotations.SerializedName

data class LoginResult(
    val createdAt: String,
    val objectId: String,
    val phone: String,
    val sessionToken: String,
    val updatedAt: String,
    @SerializedName("name") val userName: String,
    @SerializedName("timezone") val timeZone: String
)