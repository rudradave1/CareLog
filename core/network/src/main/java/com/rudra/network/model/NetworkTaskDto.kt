package com.rudra.network.model

/**
 * Network representation of a Task.
 *
 * Dates/times are ISO-8601 strings to avoid timezone ambiguity.
 */
data class NetworkTaskDto(
    val id: String,
    val title: String,
    val category: String,
    val frequency: String,
    val startDate: String,
    val reminderTime: String?,
    val completedAt: String?,
    val createdAt: Long,
    val updatedAt: Long
)
