package com.rudra.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rudra.designsystem.components.EmptyState
import com.rudra.designsystem.components.LoadingState
import com.rudra.designsystem.components.TaskCard
import com.rudra.designsystem.theme.CareLogScaffold
import com.rudra.designsystem.theme.Spacing
import com.rudra.domain.Task
import com.rudra.tasks.state.TaskListUiState
import com.rudra.tasks.viewmodel.TaskListViewModel
import java.util.UUID

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onAddTaskClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CareLogScaffold(
        title = "Tasks",
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task"
                )
            }
        }
    ) {
        when (uiState) {

            TaskListUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is TaskListUiState.Success -> {
                val tasks = (uiState as TaskListUiState.Success).tasks

                if (tasks.isEmpty()) {
                    EmptyState(
                        message = "No tasks yet.\nTap + to add your first task."
                    )
                } else {
                    TaskList(
                        tasks = tasks,
                        onComplete = viewModel::completeTask
                    )
                }
            }

            is TaskListUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as TaskListUiState.Error).message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskList(
    tasks: List<Task>,
    onComplete: (UUID) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.md)
    ) {
        items(
            items = tasks,
            key = { it.id }
        ) { task ->
            TaskItem(
                task = task,
                onComplete = onComplete
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onComplete: (UUID) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    if (!task.isCompleted) {
                        onComplete(task.id)
                    }
                }
            )

            Spacer(modifier = Modifier.width(Spacing.sm))

            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration =
                        if (task.isCompleted)
                            TextDecoration.LineThrough
                        else TextDecoration.None
                )

                task.lastCompletedAt?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Completed on $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}