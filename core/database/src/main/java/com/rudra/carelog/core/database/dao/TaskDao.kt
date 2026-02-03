package com.rudra.carelog.core.database.dao


import androidx.room.*
import com.rudra.carelog.core.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID


@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE completedAt IS NULL ORDER BY createdAt DESC")
    fun observeActiveTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id IN (:taskIds)")
    suspend fun getTasksByIds(taskIds: List<UUID>): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: UUID): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTasks(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks WHERE pendingSync = 1")
    suspend fun getPendingTasks(): List<TaskEntity>

    @Query("SELECT COUNT(*) FROM tasks WHERE pendingSync = 1")
    fun observePendingCount(): Flow<Int>

    @Query(
        """
    UPDATE tasks
    SET completedAt = :completedAt,
        updatedAt = :updatedAt,
        pendingSync = 1
    WHERE id = :taskId
    """
    )
    suspend fun markTaskCompleted(
        taskId: UUID,
        completedAt: String,
        updatedAt: Long
    )
    @Query(
        """
    UPDATE tasks
    SET completedAt = NULL,
        updatedAt = :updatedAt,
        pendingSync = 1
    WHERE id = :taskId
    """
    )
    suspend fun restoreTask(
        taskId: UUID,
        updatedAt: Long
    )

    @Query(
        """
    UPDATE tasks
    SET pendingSync = 0,
        lastSyncedAt = :syncedAt
    WHERE id IN (:taskIds)
    """
    )
    suspend fun markTasksSynced(
        taskIds: List<UUID>,
        syncedAt: Long
    )
}
