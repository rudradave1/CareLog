package com.rudra.carelog.di

import android.content.Context
import com.rudra.carelog.core.database.provider.DatabaseProvider
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.carelog.core.database.repository.TaskSyncRepository
import com.rudra.carelog.core.database.repository.impl.TaskRepositoryImpl
import com.rudra.carelog.core.database.repository.impl.TaskSyncRepositoryImpl
import com.rudra.common.preferences.UserPreferences
import com.rudra.common.preferences.SyncPreferences
import com.rudra.network.NetworkProvider

class AppContainer(context: Context) {

    private val taskDao =
        DatabaseProvider.provideTaskDao(context)

    val taskRepository: TaskRepository =
        TaskRepositoryImpl(taskDao)

    private val syncPreferences =
        SyncPreferences(context)

    private val syncApi =
        NetworkProvider.provideTaskSyncApi(context)

    val taskSyncRepository: TaskSyncRepository =
        TaskSyncRepositoryImpl(
            taskDao = taskDao,
            syncApi = syncApi,
            syncPreferences = syncPreferences
        )

    val userPreferences =
        UserPreferences(context)
}
