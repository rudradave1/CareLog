package com.rudra.carelog.core.database.provider

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rudra.carelog.core.database.CareLogDatabase
import com.rudra.carelog.core.database.dao.TaskDao

object DatabaseProvider {

    private val MIGRATION_3_4 =
        object : Migration(3, 4) {
            override fun migrate(
                database: SupportSQLiteDatabase
            ) {
                database.execSQL(
                    """
                    ALTER TABLE tasks
                    ADD COLUMN pendingSync INTEGER NOT NULL DEFAULT 1
                    """.trimIndent()
                )
                database.execSQL(
                    """
                    ALTER TABLE tasks
                    ADD COLUMN lastSyncedAt INTEGER
                    """.trimIndent()
                )
            }
        }

    fun provideTaskDao(context: Context): TaskDao {
        val db = Room.databaseBuilder(
            context.applicationContext,
            CareLogDatabase::class.java,
            "carelog_db"
        )
        .addMigrations(MIGRATION_3_4)
        .build()
        return db.taskDao()
    }
}
