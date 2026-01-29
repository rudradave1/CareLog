package com.rudra.designsystem.components


import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rudra.domain.TaskCategory

@Composable
fun CategoryChip(
    category: TaskCategory,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = category.name.lowercase()
                    .replaceFirstChar { it.uppercase() }
            )
        },
        modifier = modifier
    )
}