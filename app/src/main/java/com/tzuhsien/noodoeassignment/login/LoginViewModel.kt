package com.tzuhsien.noodoeassignment.login

import androidx.lifecycle.*
import com.tzuhsien.amazingtalker.util.Util
import com.tzuhsien.noodoeassignment.data.Result
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.data.model.LoginInput
import com.tzuhsien.noodoeassignment.data.source.Repository
import com.tzuhsien.noodoeassignment.network.LoadApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

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
            Util.getString(R.string.please_input_to_login)
        } else if (userName.value.isNullOrEmpty()) {
            Util.getString(R.string.invalid_user_name)
        } else if (password.value.isNullOrEmpty()){
            Util.getString(R.string.invalid_password)
        } else {
            null
        }
    }

    fun logIn() {

        if (null != userName.value && null != password.value) {

            viewModelScope.launch {

                Timber.d("login fun called")

                _status.value = LoadApiStatus.LOADING

                val result = withContext(Dispatchers.IO) {
                    repository.logIn(
                        LoginInput(userName.value!!, password.value!!)
                    )
                }

                when (result) {
                    is Result.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE

                        Timber.d("result.data = ${result.data}")
                        result.data
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        _status.value = LoadApiStatus.ERROR
                        Timber.d("Log in Result.Fail: ${error.value}")
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _error.value = Util.getString(R.string.unknown_error)
                        _status.value = LoadApiStatus.ERROR
                    }
                }
            }
        }
    }

}