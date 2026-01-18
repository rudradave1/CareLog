package com.rudra.carelog.core.database.domain.model

import com.rudra.carelog.core.database.domain.enums.TaskCategory
import com.rudra.carelog.core.database.domain.enums.TaskFrequency
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Task(
    val id: UUID,
    val title: String,
    val category: TaskCategory,
    val frequency: TaskFrequency,
    val startDate: LocalDate,
    val reminderTime: LocalTime?,
    val lastCompletedAt: LocalDate?,
    val isActive: Boolean,
    val updatedAt: Long
)