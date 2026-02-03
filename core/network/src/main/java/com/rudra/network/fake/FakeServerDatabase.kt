package com.rudra.network.fake

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FakeServerTaskEntity::class, FakeServerMetaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FakeServerDatabase : RoomDatabase() {
    abstract fun dao(): FakeServerDao
}
