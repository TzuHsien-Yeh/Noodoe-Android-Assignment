package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.Result.*
import com.tzuhsien.noodoeassignment.data.UserInfo
import com.tzuhsien.noodoeassignment.data.model.*
import com.tzuhsien.noodoeassignment.data.source.local.UserManager

class FakeRepository : Repository {

    private var shouldReturnNetworkError = false

    private var fakeUser: UserInfo? = UserInfo("a", "b", "c", "d")

    val loginResult = LoginResult(
        "a",
    "b",
    "c",
    "d",
    "e",
    "f",
    "g")

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    fun setUserInfo(userInfo: UserInfo?) {
        fakeUser = userInfo
    }

    override suspend fun logIn(userInput: LoginInput): Result<LoginResult> {
        return if (shouldReturnNetworkError) {
            Fail("Error")
        } else {
            Result.Success(loginResult)
        }
    }

    override fun getUserInfo(): UserInfo? {
        return fakeUser
    }

    override suspend fun getParkingInfo(): Result<List<ParkingInfoToDisplay>> {
        return if (shouldReturnNetworkError) {
            Fail("Error")
        } else {
            Result.Success(listOf<ParkingInfoToDisplay>())
        }
    }

    override suspend fun updateUserTimeZone(
        timeZone: TimeZone,
        objectId: String,
        sessionToken: String,
    ): Result<UpdateResponse> {
        return if (shouldReturnNetworkError) {
            Fail("Error")
        } else {
            Result.Success(UpdateResponse("2023/02"))
        }
    }
}