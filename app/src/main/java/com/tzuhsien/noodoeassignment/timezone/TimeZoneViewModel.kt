package com.tzuhsien.noodoeassignment.timezone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tzuhsien.noodoeassignment.data.source.Repository
import com.tzuhsien.noodoeassignment.data.source.local.UserManager.user
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeZoneViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val user = repository.getUserInfo()

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    init {
        checkUserLoggedIn()
    }

    fun checkUserLoggedIn(){
        _isLoggedIn.value = null != user
    }



}