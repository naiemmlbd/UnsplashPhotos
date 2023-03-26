package com.example.unsplashphotos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UnsplashApp() : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
