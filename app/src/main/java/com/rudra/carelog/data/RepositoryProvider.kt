package com.rudra.carelog.data

import android.content.Context
import com.rudra.carelog.core.database.provider.DatabaseProvider
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.carelog.core.database.repository.impl.TaskRepositoryImpl

object RepositoryProvider {

    fun provideTaskRepository(context: Context): TaskRepository {
        return TaskRepositoryImpl(
            taskDao = DatabaseProvider.provideTaskDao(context)
        )
    }
}
