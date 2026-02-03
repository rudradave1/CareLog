package com.rudra.network.fake

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_tasks")
data class FakeServerTaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String,
    val frequency: String,
    val startDate: String,
    val reminderTime: String?,
    val completedAt: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)
