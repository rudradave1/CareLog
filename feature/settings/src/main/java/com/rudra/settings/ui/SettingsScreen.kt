package com.rudra.settings.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.rudra.designsystem.theme.Spacing
import com.rudra.settings.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val remindersEnabled by
        viewModel.remindersEnabled
            .collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.md)
    ) {
        Text(
            text = "Enable reminders",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(Spacing.sm))

        Switch(
            checked = remindersEnabled,
            onCheckedChange =
                viewModel::onRemindersToggled
        )
    }
}
