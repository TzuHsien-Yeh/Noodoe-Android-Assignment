package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoResult
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoToDisplay

interface Repository {

    suspend fun logIn(userInput: LoginInput): Result<LoginResult>

    suspend fun getParkingInfo(): Result<List<ParkingInfoToDisplay>>

}