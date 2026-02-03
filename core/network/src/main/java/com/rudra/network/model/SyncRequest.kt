package com.rudra.network.model

/**
 * Client sends local changes plus its last sync time.
 */
data class SyncRequest(
    val lastSyncTime: Long?,
    val changes: List<NetworkTaskDto>
)
