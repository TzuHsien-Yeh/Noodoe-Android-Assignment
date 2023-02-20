package com.tzuhsien.noodoeassignment.data.source.remote

import com.tzuhsien.noodoeassignment.util.Util
import com.tzuhsien.noodoeassignment.Constants
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.*
import com.tzuhsien.noodoeassignment.data.source.DataSource
import com.tzuhsien.noodoeassignment.network.NoodoeApiService
import com.tzuhsien.noodoeassignment.network.ParkingApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val noodoeApiService: NoodoeApiService,
    private val parkingApiService: ParkingApiService,
) : DataSource {
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

    override suspend fun getParkingInfo(): Result<List<ParkingInfoToDisplay>> =
        withContext(Dispatchers.IO) {

            if (!Util.isInternetConnected()) {
                return@withContext Result.Fail(Util.getString(R.string.internet_not_connected))
            }

            try {
                val parkingInfo = async { parkingApiService.getParkingInfo() }
                val available = async { parkingApiService.getAvailableSpots() }

                val result = assembleParkingInfo(parkingInfo.await(), available.await())

                Result.Success(result)

            } catch (e: HttpException) {
                Timber.w(" exception=${e.message}")
                Result.Error(e)
            }

        }

    override suspend fun updateUserTimeZone(
        timeZone: TimeZone,
        objectId: String,
        sessionToken: String,
    ): Result<UpdateResponse> =
        withContext(Dispatchers.IO) {

            if (!Util.isInternetConnected()) {
                return@withContext Result.Fail(Util.getString(R.string.internet_not_connected))
            }

            try {

                val result = noodoeApiService.updateUserTimeZone(
                    applicationId = Constants.applicationId,
                    objectId = objectId,
                    sessionToken = sessionToken,
                    timeZone = timeZone
                )

                Result.Success(result)

            } catch (e: HttpException) {
                Timber.w(" exception=${e.message}")
                Result.Error(e)
            }

        }

    private fun assembleParkingInfo(
        parkingInfo: ParkingInfoResult,
        available: AvailableSpaceResult,
    ): List<ParkingInfoToDisplay> {

        val listToDisplay = mutableListOf<ParkingInfoToDisplay>()

        for (park in parkingInfo.data.park) {
            for (available in available.data.park) {

                if (park.id == available.id) {
                    val parkingLot = ParkingInfoToDisplay(
                        id = park.id,
                        name = park.name,
                        address = park.address,
                        totalCar = park.totalCar,
                        availableCar = available.availableCar,
                        socketQty = if (null == available.chargeStation?.socketStatusList) null else available.chargeStation.socketStatusList.size,
                        socketInUse = if (null == available.chargeStation?.socketStatusList) null else calculateSocketInUse(
                            available.chargeStation.socketStatusList),
                        socketAvailable = if (null == available.chargeStation?.socketStatusList) null else calculateSocketAvailable(
                            available.chargeStation.socketStatusList),
                    )

                    Timber.d("available.chargeStation = ${available.chargeStation}, list =${available.chargeStation?.socketStatusList} ")
                    listToDisplay.add(parkingLot)
                }
            }
        }
        return listToDisplay
    }

    private fun calculateSocketInUse(socketStatusList: List<SocketStatus>): Int {
        if (socketStatusList.isEmpty()) return 0
        var inUse = 0
        var available = 0
        for (s in socketStatusList) {
            when (s.spotStatus) {
                "充電中" -> inUse++
                "待機中" -> available++
            }
        }
        return inUse
    }

    private fun calculateSocketAvailable(socketStatusList: List<SocketStatus>): Int {
        if (socketStatusList.isEmpty()) return 0
        var available = 0
        for (s in socketStatusList) {
            when (s.spotStatus) {
                "待機中" -> available++
            }
        }
        return available
    }
}