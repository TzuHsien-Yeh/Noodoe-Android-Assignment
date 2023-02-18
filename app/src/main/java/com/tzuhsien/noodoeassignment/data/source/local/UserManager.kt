package com.tzuhsien.noodoeassignment.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.tzuhsien.noodoeassignment.MyApplication

object UserManager {
    // Keys put in the USER sharedPref
    private const val USER_INFO = "USER"
    private const val USER_NAME = "userName"
    private const val TIME_ZONE = "timeZone"
    private const val OBJECT_ID = "objectId"

    private val preferences: SharedPreferences =
        MyApplication.instance.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)

    private val editor = preferences.edit()

    var userName: String? = null
        get() {
            return preferences.getString(USER_NAME, null)
        }
        set(value) {
            field = value
            editor.putString(USER_NAME, value).commit()
        }

    var objectId: String? = null
        get() {
            return preferences.getString(OBJECT_ID, null)
        }
        set(value) {
            field = value
            editor.putString(OBJECT_ID, value).commit()
        }

    var timeZone: String? = null
        get() {
            return preferences.getString(TIME_ZONE, null)
        }
        set(value) {
            field = value
            editor.putString(TIME_ZONE, value).commit()
        }


    var sessionToken: String? = null

    fun checkLoginState(): Boolean {
        return !sessionToken.isNullOrEmpty()
    }

}