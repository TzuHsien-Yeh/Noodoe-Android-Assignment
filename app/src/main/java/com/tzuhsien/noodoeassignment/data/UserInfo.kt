package com.tzuhsien.noodoeassignment.data

data class UserInfo (
    val userName: String,
    val objectId: String,
    var timeZone: String,
    val sessionToken: String
)