package com.tzuhsien.noodoeassignment.login

import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.util.Util

enum class InputError(val msg: String) {
    INVALID_USER("Invalid user name"),
    INVALID_PASSWORD("Please enter your password"),
    NO_INPUT("Please Input to login")
}