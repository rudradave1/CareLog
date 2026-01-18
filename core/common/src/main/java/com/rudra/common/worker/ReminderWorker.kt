package com.rudra.common.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.rudra.notifications.NotificationHelper
import java.util.concurrent.TimeUnit

import androidx.work.*
class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val title =
            inputData.getString(KEY_TASK_TITLE)
                ?: return Result.failure()

        NotificationHelper.showTaskReminder(
            context = applicationContext,
            title = title
        )

        return Result.success()
    }
    companion object {
        const val KEY_TASK_TITLE = "task_title"
    }
}

fun scheduleReminder(
    context: Context,
    taskId: String,
    title: String
) {
    val constraints =
        Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

    val request =
        OneTimeWorkRequestBuilder<ReminderWorker>()
            .setConstraints(constraints)
            .setInputData(
                workDataOf(
                    ReminderWorker.KEY_TASK_TITLE to title
                )
            )
            .setInitialDelay(1, TimeUnit.DAYS)
            .addTag("task_reminder")
            .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "task_reminder_$taskId",
            ExistingWorkPolicy.REPLACE,
            request
        )
}

