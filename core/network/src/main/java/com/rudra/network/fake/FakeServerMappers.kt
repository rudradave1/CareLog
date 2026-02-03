package com.rudra.network.fake

import com.rudra.network.model.NetworkTaskDto

fun FakeServerTaskEntity.toDto(): NetworkTaskDto {
    return NetworkTaskDto(
        id = id,
        title = title,
        category = category,
        frequency = frequency,
        startDate = startDate,
        reminderTime = reminderTime,
        completedAt = completedAt,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}

fun NetworkTaskDto.toServerEntity(): FakeServerTaskEntity {
    return FakeServerTaskEntity(
        id = id,
        title = title,
        category = category,
        frequency = frequency,
        startDate = startDate,
        reminderTime = reminderTime,
        completedAt = completedAt,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}
