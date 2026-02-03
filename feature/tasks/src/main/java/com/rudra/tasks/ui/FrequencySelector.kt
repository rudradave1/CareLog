package com.rudra.tasks.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            FilterChip(
                selected = selected is TaskFrequency.Daily,
                onClick = {
                    onChange(TaskFrequency.Daily)
                },
                label = { Text("Daily") }
            )

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
