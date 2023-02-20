package com.tzuhsien.noodoeassignment.network

import com.tzuhsien.noodoeassignment.data.model.AvailableSpaceResult
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ParkingApiService {

    companion object{
        private const val ENDPOINT_PARKING_INFO = "TCMSV_alldesc.json"
        private const val ENDPOINT_AVAILABLE = "TCMSV_allavailable.json"
    }

    @GET(ENDPOINT_PARKING_INFO)
    suspend fun getParkingInfo(): ParkingInfoResult

    @GET(ENDPOINT_AVAILABLE)
    suspend fun getAvailableSpots(): AvailableSpaceResult

}