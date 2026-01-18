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
        frequency = TaskFrequency.Daily, // simplified for now
        startDate = LocalDate.parse(startDate),
        reminderTime = reminderTime?.let { LocalTime.parse(it) },
        lastCompletedAt = lastCompletedAt?.let { LocalDate.parse(it) },
        isActive = isActive,
        updatedAt = updatedAt
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        category = category.name,
        frequency = frequency.toString(),
        startDate = startDate.toString(),
        reminderTime = reminderTime?.toString(),
        lastCompletedAt = lastCompletedAt?.toString(),
        isActive = isActive,
        updatedAt = updatedAt
    )
}
