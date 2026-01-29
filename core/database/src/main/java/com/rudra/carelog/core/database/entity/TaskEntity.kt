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

    val frequencyType: String,   // DAILY / WEEKLY / MONTHLY
    val frequencyData: String?,  // encoded payload

    val startDate: String,
    val reminderTime: String?,

    val completedAt: String?,    // null = active
    val createdAt: Long,
    val updatedAt: Long
)