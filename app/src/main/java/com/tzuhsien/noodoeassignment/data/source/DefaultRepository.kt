package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.UserInfo
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoResult
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoToDisplay
import com.tzuhsien.noodoeassignment.data.source.local.UserManager
import timber.log.Timber
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: DataSource
): Repository {
    override suspend fun logIn(userInput: LoginInput): Result<LoginResult> {
        val loginResult = remoteDataSource.logIn(userInput)

         when (loginResult) {
            is Result.Success -> {
                UserManager.user = UserInfo(
                    userName = loginResult.data.userName,
                    objectId = loginResult.data.objectId,
                    timeZone = loginResult.data.timeZone,
                    sessionToken = loginResult.data.sessionToken
                )

                Timber.d("UserManager.user = ${UserManager.user}")
            }
            else -> {}
        }

        return loginResult
    }

    override fun getUserInfo(): UserInfo? {
        return UserManager.user
    }

    override suspend fun getParkingInfo(): Result<List<ParkingInfoToDisplay>> {
        return remoteDataSource.getParkingInfo()
    }


}