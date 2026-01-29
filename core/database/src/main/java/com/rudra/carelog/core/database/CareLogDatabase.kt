package com.rudra.carelog.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rudra.carelog.core.database.converter.Converters
import com.rudra.carelog.core.database.dao.TaskDao
import com.rudra.carelog.core.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CareLogDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

