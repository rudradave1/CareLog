package com.rudra.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.rudra.notifications.NotificationChannels.TASK_REMINDERS

object NotificationHelper {

    fun showTaskReminder(
        context: Context,
        title: String
    ) {
        val notification =
            NotificationCompat.Builder(context, TASK_REMINDERS)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Task reminder")
                .setContentText(title)
                .setAutoCancel(true)
                .build()

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        manager.notify(
            title.hashCode(),
            notification
        )
    }
}
