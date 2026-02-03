package com.rudra.common.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.syncDataStore by preferencesDataStore(
    name = "sync_preferences"
)

class SyncPreferences(
    private val context: Context
) {
    companion object {
        val LAST_SYNC_TIME =
            longPreferencesKey("last_sync_time")
    }

    val lastSyncTime: Flow<Long?> =
        context.syncDataStore.data.map { prefs ->
            prefs[LAST_SYNC_TIME]
        }

    suspend fun setLastSyncTime(
        timestamp: Long
    ) {
        context.syncDataStore.edit { prefs ->
            prefs[LAST_SYNC_TIME] = timestamp
        }
    }
}
