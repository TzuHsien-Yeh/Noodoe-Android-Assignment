package com.tzuhsien.noodoeassignment.parkinginfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzuhsien.noodoeassignment.util.Util
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.model.ParkingInfoToDisplay
import com.tzuhsien.noodoeassignment.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ParkingInfoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _parkingLotList = MutableLiveData<List<ParkingInfoToDisplay>?>()
    val parkingLotList: LiveData<List<ParkingInfoToDisplay>?>
        get() = _parkingLotList

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    init {
        getParkingInfoData()
    }

    fun getParkingInfoData() {

        viewModelScope.launch {
            val result = repository.getParkingInfo()

            when (result) {
                is Result.Success -> {
                    _error.value = null
                    Timber.d("result.data = ${result.data}")
                    _parkingLotList.value = result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    Timber.d("Result.Fail: ${error.value}")
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                }
                else -> {
                    _error.value = Util.getString(R.string.unknown_error)
                }
            }
        }

    }
}