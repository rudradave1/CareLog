package com.rudra.carelog

import android.app.Application
import com.rudra.carelog.di.AppContainer
import com.rudra.common.worker.cancelAllReminders
import com.rudra.carelog.sync.SyncScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CareLogApp : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(this)

        observeReminderPreference()
        SyncScheduler.schedulePeriodicSync(this)
    }

    private fun observeReminderPreference() {
        CoroutineScope(Dispatchers.Default).launch {
            appContainer.userPreferences
                .remindersEnabled
                .collect { enabled ->
                    if (!enabled) {
                        cancelAllReminders(this@CareLogApp)
                    }
                }
        }
    }
}
