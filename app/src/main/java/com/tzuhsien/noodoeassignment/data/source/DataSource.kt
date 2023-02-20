package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.*

interface DataSource {

    suspend fun logIn(userInput: LoginInput): Result<LoginResult>

    suspend fun getParkingInfo(): Result<List<ParkingInfoToDisplay>>

    suspend fun updateUserTimeZone(timeZone: TimeZone, objectId: String, sessionToken: String): Result<UpdateResponse>

}