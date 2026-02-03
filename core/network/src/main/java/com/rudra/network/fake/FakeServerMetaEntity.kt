package com.rudra.network.fake

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_meta")
data class FakeServerMetaEntity(
    @PrimaryKey
    val key: String,
    val longValue: Long
)
