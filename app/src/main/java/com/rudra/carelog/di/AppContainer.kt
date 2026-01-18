package com.rudra.carelog.di

import android.content.Context
import com.rudra.carelog.core.database.provider.DatabaseProvider
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.carelog.core.database.repository.impl.TaskRepositoryImpl
import com.rudra.common.preferences.UserPreferences

class AppContainer(context: Context) {

    private val taskDao =
        DatabaseProvider.provideTaskDao(context)

    val taskRepository: TaskRepository =
        TaskRepositoryImpl(taskDao)

    val userPreferences =
        UserPreferences(context)
}
