package com.rudra.designsystem.components


import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier.semantics {
            contentDescription = "Category ${category.name.lowercase()}"
        }
    )
}
