package com.tzuhsien.noodoeassignment.login

import androidx.lifecycle.*
import com.tzuhsien.noodoeassignment.util.Util
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _navigateToParkingInfo = MutableLiveData<Boolean>(false)
    val navigateToParkingInfo: LiveData<Boolean>
        get() = _navigateToParkingInfo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    fun checkToLogIn(){
        if (checkInputValidity()) {
            logIn()
        } else {
            showErrorMsg()
        }
    }

    private fun checkInputValidity(): Boolean {
        return !(userName.value.isNullOrEmpty() || password.value.isNullOrEmpty())
    }

    private fun showErrorMsg() {
        _error.value = if (userName.value.isNullOrEmpty() && password.value.isNullOrEmpty()) {
            InputError.NO_INPUT.msg
        } else if (userName.value.isNullOrEmpty()) {
            InputError.INVALID_USER.msg
        } else if (password.value.isNullOrEmpty()){
            InputError.INVALID_PASSWORD.msg
        } else {
            null
        }
    }

    fun logIn() {

        if (null != userName.value && null != password.value) {

            viewModelScope.launch {

                Timber.d("login fun called")

                val result = repository.logIn(
                        LoginInput(userName.value!!, password.value!!)
                    )

                when (result) {
                    is Result.Success -> {
                        _error.value = null

                        Timber.d("result.data = ${result.data}")

                        navigateToParkingInfo()
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        Timber.d("Log in Result.Fail: ${error.value}")
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

    private fun navigateToParkingInfo(){
        _navigateToParkingInfo.value = true
    }

    fun doneNavigation() {
        _navigateToParkingInfo.value = false
    }

}