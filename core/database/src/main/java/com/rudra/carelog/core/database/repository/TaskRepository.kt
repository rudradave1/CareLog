package com.rudra.carelog.core.database.repository

import com.rudra.carelog.core.database.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TaskRepository {

    fun observeTasks(): Flow<List<Task>>

    suspend fun getTask(id: UUID): Task?

    suspend fun saveTask(task: Task)

    suspend fun completeTask(id: UUID)
}