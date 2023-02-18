package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.amazingtalker.util.Util
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import com.tzuhsien.noodoeassignment.data.source.local.UserManager
import com.tzuhsien.noodoeassignment.network.LoadApiStatus
import timber.log.Timber
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: DataSource
): Repository {
    override suspend fun logIn(userInput: LoginInput): Result<LoginResult> {
        val loginResult = remoteDataSource.logIn(userInput)

         when (loginResult) {
            is Result.Success -> {
                UserManager.userName = loginResult.data.userName
                UserManager.timeZone = loginResult.data.timeZone
                UserManager.sessionToken = loginResult.data.sessionToken
                
                Timber.d("UserManager.timeZone = ${UserManager.timeZone}, UserManager.sessionToken = ${UserManager.sessionToken}")
            }
            else -> {}
        }

        Timber.d("UserManager.timeZone = ${UserManager.timeZone}, UserManager.sessionToken = ${UserManager.sessionToken}")

        return loginResult
    }
}