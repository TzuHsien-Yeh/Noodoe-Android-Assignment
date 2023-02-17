package com.tzuhsien.noodoeassignment.data.model

data class LoginResult(
    val createdAt: String,
    val objectId: String,
    val phone: String,
    val sessionToken: String,
    val updatedAt: String,
    val username: String
)