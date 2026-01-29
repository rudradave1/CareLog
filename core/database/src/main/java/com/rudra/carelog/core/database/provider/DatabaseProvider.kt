package com.rudra.carelog.core.database.provider

import android.content.Context
import androidx.room.Room
import com.rudra.carelog.core.database.CareLogDatabase
import com.rudra.carelog.core.database.dao.TaskDao

object DatabaseProvider {

    fun provideTaskDao(context: Context): TaskDao {
        val db = Room.databaseBuilder(
            context.applicationContext,
            CareLogDatabase::class.java,
            "carelog_db"
        )
        .fallbackToDestructiveMigration() // ⬅️ REQUIRED
        .build()
        return db.taskDao()
    }
}

