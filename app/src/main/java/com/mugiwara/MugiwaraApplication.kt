package com.mugiwara

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MugiwaraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
