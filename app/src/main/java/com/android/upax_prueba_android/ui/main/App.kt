package com.android.upax_prueba_android.ui.main

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
    }

    companion object {
        lateinit var instance: App
        fun getContext(): Context? {
            return instance.applicationContext
        }
    }
}
