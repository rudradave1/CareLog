package com.rudra.network.model

/**
 * Server responds with changes since last sync and a server time anchor.
 */
data class SyncResponse(
    val serverTime: Long,
    val tasks: List<NetworkTaskDto>,
    // Optional: if provided, only these local changes are confirmed as applied.
    val appliedIds: List<String> = emptyList()
)
