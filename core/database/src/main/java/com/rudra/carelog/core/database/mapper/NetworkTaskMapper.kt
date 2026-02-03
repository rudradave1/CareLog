package com.rudra.carelog.core.database.mapper

import com.rudra.carelog.core.database.entity.TaskEntity
import com.rudra.network.model.NetworkTaskDto
import java.util.UUID

fun TaskEntity.toNetworkDto(): NetworkTaskDto {
    return NetworkTaskDto(
        id = id.toString(),
        title = title,
        category = category,
        frequency = frequency,
        startDate = startDate,
        reminderTime = reminderTime,
        completedAt = completedAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun NetworkTaskDto.toEntity(
    pendingSync: Boolean = false,
    lastSyncedAt: Long? = null
): TaskEntity {
    return TaskEntity(
        id = UUID.fromString(id),
        title = title,
        category = category,
        frequency = frequency,
        startDate = startDate,
        reminderTime = reminderTime,
        completedAt = completedAt,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pendingSync = pendingSync,
        lastSyncedAt = lastSyncedAt
    )
}
