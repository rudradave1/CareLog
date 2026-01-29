package com.rudra.domain

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
) {
    val isCompleted: Boolean
        get() = lastCompletedAt != null
}