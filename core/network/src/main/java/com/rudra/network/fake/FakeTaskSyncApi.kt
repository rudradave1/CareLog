package com.rudra.network.fake

import android.content.Context
import androidx.room.Room
import com.rudra.network.api.TaskSyncApi
import com.rudra.network.model.SyncRequest
import com.rudra.network.model.SyncResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class FakeTaskSyncApi(
    context: Context
) : TaskSyncApi {

    // File-backed DB to simulate a real server source of truth in debug builds.
    private val database =
        Room.databaseBuilder(
            context.applicationContext,
            FakeServerDatabase::class.java,
            "carelog_fake_server_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    private val dao = database.dao()

    override suspend fun syncTasks(
        request: SyncRequest
    ): SyncResponse = withContext(Dispatchers.IO) {
        val serverTime = getServerTime()
        val appliedIds = mutableListOf<String>()

        // Apply incoming changes to the server state with updatedAt conflict resolution.
        request.changes.forEach { incoming ->
            val existing = dao.getTask(incoming.id)
            if (existing == null || incoming.updatedAt > existing.updatedAt) {
                dao.upsertTask(incoming.toServerEntity())
                appliedIds.add(incoming.id)
            }
        }

        // Respond with server-side changes since last sync.
        val since = request.lastSyncTime ?: 0L
        val serverChanges = if (request.lastSyncTime == null) {
            dao.getAllTasks()
        } else {
            dao.getTasksUpdatedAfter(since)
        }

        SyncResponse(
            serverTime = serverTime,
            tasks = serverChanges.map { it.toDto() },
            appliedIds = appliedIds
        )
    }

    private suspend fun getServerTime(): Long {
        val offset = getOrCreateTimeOffset()
        return System.currentTimeMillis() + offset
    }

    private suspend fun getOrCreateTimeOffset(): Long {
        val existing = dao.getMeta(KEY_TIME_OFFSET_MS)
        if (existing != null) return existing.longValue

        // Persist a deterministic drift for this mock server instance.
        val offset = Random.nextLong(
            -MAX_DRIFT_MS,
            MAX_DRIFT_MS
        )
        dao.upsertMeta(
            FakeServerMetaEntity(
                key = KEY_TIME_OFFSET_MS,
                longValue = offset
            )
        )
        return offset
    }

    companion object {
        private const val KEY_TIME_OFFSET_MS =
            "server_time_offset_ms"
        private const val MAX_DRIFT_MS =
            5 * 60 * 1000L
    }
}
