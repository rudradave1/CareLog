package com.rudra.carelog.core.database.repository.impl

import com.rudra.carelog.core.database.dao.TaskDao
import com.rudra.carelog.core.database.entity.TaskEntity
import com.rudra.carelog.core.database.mapper.toEntity
import com.rudra.carelog.core.database.mapper.toNetworkDto
import com.rudra.common.preferences.SyncPreferences
import com.rudra.carelog.core.database.repository.SyncResult
import com.rudra.carelog.core.database.repository.TaskSyncRepository
import com.rudra.network.api.TaskSyncApi
import com.rudra.network.model.SyncRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class TaskSyncRepositoryImpl(
    private val taskDao: TaskDao,
    private val syncApi: TaskSyncApi,
    private val syncPreferences: SyncPreferences
) : TaskSyncRepository {

    override val lastSyncTime: Flow<Long?> =
        syncPreferences.lastSyncTime

    override fun observePendingCount(): Flow<Int> =
        taskDao.observePendingCount()

    override suspend fun syncNow(): SyncResult {
        return withContext(Dispatchers.IO) {
            try {
                val lastSync = syncPreferences.lastSyncTime.first()
                val pending = taskDao.getPendingTasks()

                val response = syncApi.syncTasks(
                    SyncRequest(
                        lastSyncTime = lastSync,
                        changes = pending.map { it.toNetworkDto() }
                    )
                )

                val serverTime = response.serverTime

                // Conflict strategy: newer updatedAt wins. We only apply remote
                // updates when they are newer than local to keep Room authoritative.
                val remoteChanges = response.tasks
                if (remoteChanges.isNotEmpty()) {
                    val localById = taskDao.getTasksByIds(
                        remoteChanges.map { UUID.fromString(it.id) }
                    ).associateBy { it.id }

                    val merged = mutableListOf<TaskEntity>()
                    val deletes = mutableListOf<UUID>()

                    remoteChanges.forEach { remote ->
                        val local = localById[UUID.fromString(remote.id)]
                        if (local == null || remote.updatedAt > local.updatedAt) {
                            if (remote.deletedAt != null) {
                                deletes.add(UUID.fromString(remote.id))
                            } else {
                                merged.add(
                                    remote.toEntity(
                                        pendingSync = false,
                                        lastSyncedAt = serverTime
                                    )
                                )
                            }
                        }
                    }

                    if (merged.isNotEmpty()) {
                        taskDao.upsertTasks(merged)
                    }

                    if (deletes.isNotEmpty()) {
                        taskDao.deleteTasks(deletes)
                    }
                }

                // Mark local changes as synced after the server acknowledges.
                // When available, appliedIds prevents us from clearing changes
                // that lost a conflict to a newer server version.
                val appliedIds = if (response.appliedIds.isNotEmpty()) {
                    response.appliedIds.map { UUID.fromString(it) }
                } else {
                    pending.map { it.id }
                }

                if (appliedIds.isNotEmpty()) {
                    taskDao.markTasksSynced(
                        taskIds = appliedIds,
                        syncedAt = serverTime
                    )
                }

                syncPreferences.setLastSyncTime(serverTime)

                SyncResult.Success(syncedAt = serverTime)
            } catch (t: Throwable) {
                SyncResult.Error(
                    message = t.message ?: "Sync failed"
                )
            }
        }
    }
}
