package com.rudra.carelog.di

import android.content.Context
import com.rudra.carelog.core.database.provider.DatabaseProvider
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.carelog.core.database.repository.impl.TaskRepositoryImpl

class AppContainer(context: Context) {

    private val taskDao =
        DatabaseProvider.provideTaskDao(context)

    val taskRepository: TaskRepository =
        TaskRepositoryImpl(taskDao)
}
