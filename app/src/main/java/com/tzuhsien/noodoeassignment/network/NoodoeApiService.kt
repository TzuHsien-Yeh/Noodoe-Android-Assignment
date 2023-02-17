package com.tzuhsien.noodoeassignment.network

import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private const val ENDPOINT_LOGIN = "login"
private const val ENDPOINT_USERS = "users"

interface NoodoeApiService {

    @POST(ENDPOINT_LOGIN)
    suspend fun logIn(
        @Header("X-Parse-Application-Id") applicationId: String,
        @Body loginInput: LoginInput
    ): LoginResult

}