package com.rudra.carelog.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rudra.carelog.CareLogApp
import com.rudra.carelog.core.database.repository.SyncResult

class TaskSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val appContainer =
            (applicationContext as CareLogApp).appContainer

        return when (appContainer.taskSyncRepository.syncNow()) {
            is SyncResult.Success -> Result.success()
            is SyncResult.Error -> Result.retry()
        }
    }

    companion object {
        const val UNIQUE_WORK_NAME = "task_sync"
    }
}
