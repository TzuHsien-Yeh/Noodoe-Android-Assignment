package com.tzuhsien.noodoeassignment.data.source

import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult

interface DataSource {

    suspend fun logIn(userInput: LoginInput): Result<LoginResult>

}