package com.rudra.tasks.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.rudra.designsystem.theme.Spacing
import com.rudra.domain.TaskFrequency

@Composable
fun FrequencySelector(
    selected: TaskFrequency,
    onChange: (TaskFrequency) -> Unit
) {
    Column {
        Text(
            text = "Frequency",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Row {
            FilterChip(
                selected = selected is TaskFrequency.Daily,
                onClick = {
                    onChange(TaskFrequency.Daily)
                },
                label = { Text("Daily") }
            )

            Spacer(modifier = Modifier.width(Spacing.sm))

            FilterChip(
                selected = selected is TaskFrequency.Weekly,
                onClick = {
                    onChange(TaskFrequency.Weekly(emptySet()))
                },
                label = { Text("Weekly") }
            )
        }
    }
}