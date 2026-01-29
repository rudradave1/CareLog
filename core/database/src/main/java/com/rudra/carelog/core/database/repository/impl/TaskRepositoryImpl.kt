package com.rudra.carelog.core.database.repository.impl

import com.rudra.carelog.core.database.dao.TaskDao
import com.rudra.carelog.core.database.mapper.toDomain
import com.rudra.carelog.core.database.mapper.toEntity
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.UUID

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> {
        return taskDao.observeActiveTasks()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun getTask(id: UUID): Task? {
        return taskDao.getTaskById(id)?.toDomain()
    }

    override suspend fun saveTask(task: Task) {
        taskDao.upsertTask(task.toEntity())
    }

    override suspend fun completeTask(id: UUID) {
        val today = LocalDate.now().toString()
        val now = System.currentTimeMillis()

        taskDao.markTaskCompleted(
            taskId = id,
            completedAt = today,
            updatedAt = now
        )
    }

    override suspend fun restoreTask(id: UUID) {
        taskDao.restoreTask(
            taskId = id,
            updatedAt = System.currentTimeMillis()
        )
    }
}
