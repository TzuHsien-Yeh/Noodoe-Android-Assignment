package com.tzuhsien.noodoeassignment.parkinginfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzuhsien.noodoeassignment.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingInfoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        getParkingInfoData()
    }

    private fun getParkingInfoData() {

        viewModelScope.launch {
            repository.getParkingInfo()
        }

    }
}