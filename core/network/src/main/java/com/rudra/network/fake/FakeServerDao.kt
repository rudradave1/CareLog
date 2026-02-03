package com.rudra.network.fake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FakeServerDao {

    @Query("SELECT * FROM server_tasks WHERE id = :id")
    suspend fun getTask(id: String): FakeServerTaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: FakeServerTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTasks(tasks: List<FakeServerTaskEntity>)

    @Query("SELECT * FROM server_tasks WHERE updatedAt > :since")
    suspend fun getTasksUpdatedAfter(since: Long): List<FakeServerTaskEntity>

    @Query("SELECT * FROM server_tasks")
    suspend fun getAllTasks(): List<FakeServerTaskEntity>

    @Query("SELECT * FROM server_meta WHERE key = :key")
    suspend fun getMeta(key: String): FakeServerMetaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeta(meta: FakeServerMetaEntity)
}
