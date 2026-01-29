package com.rudra.carelog.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: UUID,

    val title: String,
    val category: String,          // enum name
    val frequency: String,         // sealed → encoded string

    val startDate: String,         // ISO LocalDate
    val reminderTime: String?,     // ISO LocalTime

    val completedAt: String?,      // ISO LocalDate or null

    val createdAt: Long,
    val updatedAt: Long
)
