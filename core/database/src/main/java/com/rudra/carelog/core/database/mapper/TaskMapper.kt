package com.rudra.carelog.core.database.mapper


import com.rudra.carelog.core.database.entity.TaskEntity
import com.rudra.domain.Task
import com.rudra.domain.TaskCategory
import com.rudra.domain.TaskFrequency
import java.time.LocalDate
import java.time.LocalTime

fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        category = TaskCategory.valueOf(category),

        frequency = decodeFrequency(frequencyType, frequencyData),
        startDate = LocalDate.parse(startDate),

        reminderTime = reminderTime?.let { LocalTime.parse(it) },
        completedAt = completedAt?.let { LocalDate.parse(it) },

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Task.toEntity(): TaskEntity {
    val (type, data) = encodeFrequency(frequency)

    return TaskEntity(
        id = id,
        title = title,
        category = category.name,

        frequencyType = type,
        frequencyData = data,

        startDate = startDate.toString(),
        reminderTime = reminderTime?.toString(),

        completedAt = completedAt?.toString(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
