package com.rudra.carelog.core.database.repository

import kotlinx.coroutines.flow.Flow

sealed class SyncResult {
    data class Success(
        val syncedAt: Long
    ) : SyncResult()

    data class Error(
        val message: String
    ) : SyncResult()
}

interface TaskSyncRepository {

    val lastSyncTime: Flow<Long?>

    fun observePendingCount(): Flow<Int>

    suspend fun syncNow(): SyncResult
}
