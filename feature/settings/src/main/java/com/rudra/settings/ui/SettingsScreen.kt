package com.rudra.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rudra.designsystem.theme.PrimaryButton
import com.rudra.designsystem.theme.Spacing
import com.rudra.settings.viewmodel.SettingsViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val remindersEnabled by
    viewModel.remindersEnabled.collectAsStateWithLifecycle()
    val lastSyncTime by viewModel.lastSyncTime.collectAsStateWithLifecycle()
    val pendingCount by viewModel.pendingCount.collectAsStateWithLifecycle()
    val syncing by viewModel.syncing.collectAsStateWithLifecycle()
    val syncError by viewModel.syncError.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.md)
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = "Control reminders for your tasks.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Task reminders",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = remindersEnabled,
                onCheckedChange =
                    viewModel::onRemindersToggled
            )
        }

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = if (remindersEnabled) {
                "Reminders will be scheduled at your task time."
            } else {
                "Reminders are paused. You can turn them back on anytime."
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = "Sync",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = "Back up tasks and keep your devices aligned.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        PrimaryButton(
            text = if (syncing) "Syncing..." else "Sync now",
            enabled = !syncing,
            onClick = viewModel::onSyncNow,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = buildSyncStatusText(
                lastSyncTime = lastSyncTime,
                pendingCount = pendingCount
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        syncError?.let { error ->
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

private fun buildSyncStatusText(
    lastSyncTime: Long?,
    pendingCount: Int
): String {
    val timeText = if (lastSyncTime == null) {
        "Never synced"
    } else {
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
        val dateTime = Instant.ofEpochMilli(lastSyncTime)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        "Last synced ${formatter.format(dateTime)}"
    }

    return if (pendingCount == 0) {
        "$timeText · All changes synced"
    } else {
        "$timeText · $pendingCount pending"
    }
}
