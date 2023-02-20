package com.tzuhsien.noodoeassignment.timezone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzuhsien.noodoeassignment.util.Util
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TimeZoneViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val user = repository.getUserInfo()

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    val timeZones: Array<String> = TimeZone.getAvailableIDs()

    val originalSelectedTimeZone = user?.timeZone

    private val _doneTimeZoneUpdate = MutableLiveData<Boolean>()
    val doneTimeZoneUpdate: LiveData<Boolean>
        get() = _doneTimeZoneUpdate

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    init {
        checkUserLoggedIn()
    }

    fun checkUserLoggedIn(){
        _isLoggedIn.value = null != user
    }

    fun updateUserTimeZone(timeZone: String){

        viewModelScope.launch {
            if (timeZone != originalSelectedTimeZone && user != null) {
                val result = repository.updateUserTimeZone(
                    objectId = user.objectId,
                    sessionToken = user.sessionToken,
                    timeZone = com.tzuhsien.noodoeassignment.data.model.TimeZone(timeZone)
                )

                when (result) {
                    is Result.Success -> {
                        _error.value = null
                        Timber.d("result.data = ${result.data}")
                        _doneTimeZoneUpdate.value = true
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        _doneTimeZoneUpdate.value = false
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        _doneTimeZoneUpdate.value = false
                    }
                    else -> {
                        _error.value = Util.getString(R.string.unknown_error)
                        _doneTimeZoneUpdate.value = false
                    }
                }
            }
        }
    }

}