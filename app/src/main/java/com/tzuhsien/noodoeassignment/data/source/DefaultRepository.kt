package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: DataSource
): Repository {
    override suspend fun logIn(userInput: LoginInput): Result<LoginResult> {
        return remoteDataSource.logIn(userInput)
    }
}