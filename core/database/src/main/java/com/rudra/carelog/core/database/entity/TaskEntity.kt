package com.rudra.carelog.core.database.entity

import androidx.room.Entity
import androidx.room.ColumnInfo
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
    val updatedAt: Long,
    // Tracks local changes that still need to be synced.
    @ColumnInfo(defaultValue = "1")
    val pendingSync: Boolean = true,
    // When this record was last confirmed by the server.
    val lastSyncedAt: Long? = null
)
