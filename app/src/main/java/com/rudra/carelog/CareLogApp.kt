package com.rudra.carelog

import android.app.Application
import com.rudra.carelog.di.AppContainer

class CareLogApp : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
