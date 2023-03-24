package com.sorongos.navermapapp

import android.app.Application
import android.content.Context
import android.util.Log

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.applicationContext = applicationContext
        Log.d("myApplication","onCreate")
    }
    companion object{
        lateinit var applicationContext: Context
    }
}