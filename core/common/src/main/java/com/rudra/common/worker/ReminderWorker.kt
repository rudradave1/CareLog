package com.rudra.common.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // later: notification
        return Result.success()
    }
    fun scheduleReminder(context: Context) {
        val request =
            OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(1, TimeUnit.DAYS)
                .build()

        WorkManager.getInstance(context)
            .enqueue(request)
    }

}
