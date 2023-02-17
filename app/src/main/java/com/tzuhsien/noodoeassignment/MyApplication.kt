package com.tzuhsien.noodoeassignment

import android.app.Application
import android.content.Context
import android.util.Log
import com.tzuhsien.noodoeassignment.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import kotlin.properties.Delegates

@HiltAndroidApp
class MyApplication : Application() {

    init {
        instance = this
    }
    companion object {
        var instance: MyApplication by Delegates.notNull()

        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        // initialize timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}