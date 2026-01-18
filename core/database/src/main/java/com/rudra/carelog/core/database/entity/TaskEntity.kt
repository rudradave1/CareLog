package com.rudra.carelog.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val category: String,
    val frequency: String,
    val startDate: String,
    val reminderTime: String?,
    val lastCompletedAt: String?,
    val isActive: Boolean,
    val updatedAt: Long
)