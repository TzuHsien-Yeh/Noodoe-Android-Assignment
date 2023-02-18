package com.tzuhsien.noodoeassignment.data.source.remote

import com.tzuhsien.amazingtalker.util.Util
import com.tzuhsien.noodoeassignment.Constants
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.model.LoginResult
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoResult
import com.tzuhsien.noodoeassignment.data.source.DataSource
import com.tzuhsien.noodoeassignment.network.NoodoeApiService
import com.tzuhsien.noodoeassignment.network.ParkingApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val noodoeApiService: NoodoeApiService,
    private val parkingApiService: ParkingApiService
): DataSource {
    override suspend fun logIn(userInput: LoginInput): Result<LoginResult> =
        withContext(Dispatchers.IO) {

            if (!Util.isInternetConnected()) {
                return@withContext Result.Fail(Util.getString(R.string.internet_not_connected))
            }

            try {

                val result = noodoeApiService.logIn(Constants.applicationId, userInput)

                Result.Success(result)

            } catch (e: HttpException) {
                Timber.w(" exception=${e.message}")
                when (e.code()) {
                    404 -> Result.Fail(Util.getString(R.string.user_not_found))

                    else -> Result.Error(e)
                }
            }
        }

    override suspend fun getParkingInfo(): Result<ParkingInfoResult> = withContext(Dispatchers.IO) {

        if (!Util.isInternetConnected()) {
            return@withContext Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        try {
            val result = parkingApiService.getParkingInfo()

            Result.Success(result)

        } catch (e: HttpException) {
            Timber.w(" exception=${e.message}")
            Result.Error(e)
        }
    }

}