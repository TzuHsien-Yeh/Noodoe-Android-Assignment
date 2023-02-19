package com.tzuhsien.noodoeassignment.network

import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import com.tzuhsien.noodoeassignment.data.model.TimeZone
import com.tzuhsien.noodoeassignment.data.model.UpdateResponse
import retrofit2.http.*

private const val ENDPOINT_LOGIN = "login"
private const val ENDPOINT_USERS = "users"

interface NoodoeApiService {

    @POST(ENDPOINT_LOGIN)
    suspend fun logIn(
        @Header("X-Parse-Application-Id") applicationId: String,
        @Body loginInput: LoginInput
    ): LoginResult

    @PUT("$ENDPOINT_USERS/{user_object_id}" )
    suspend fun updateUserTimeZone(
        @Path("user_object_id") objectId: String,
        @Header("X-Parse-Application-Id") applicationId: String,
        @Header("X-Parse-Session-Token") sessionToken: String,
        @Body timeZone: TimeZone
    ): UpdateResponse

}