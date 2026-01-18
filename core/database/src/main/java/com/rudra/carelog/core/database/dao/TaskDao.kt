package com.rudra.carelog.core.database.dao


import androidx.room.*
import com.rudra.carelog.core.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE isActive = 1")
    fun observeActiveTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: UUID): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: TaskEntity)

    @Query("UPDATE tasks SET lastCompletedAt = :completedAt WHERE id = :taskId")
    suspend fun markTaskCompleted(
        taskId: UUID,
        completedAt: String
    )
}