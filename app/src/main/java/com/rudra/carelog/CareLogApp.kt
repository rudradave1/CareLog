package com.rudra.carelog

import android.app.Application
import com.rudra.carelog.di.AppContainer
import com.rudra.notifications.NotificationChannels

class CareLogApp : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
        appContainer = AppContainer(this)
    }
}
