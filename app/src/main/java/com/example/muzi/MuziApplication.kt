package com.example.muzi

import android.app.Application
import android.content.Context

class MuziApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MuziApplication
    }
}
