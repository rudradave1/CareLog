package com.rudra.tasks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.rudra.designsystem.components.EmptyState
import com.rudra.designsystem.theme.LocalSnackbarHostState
import com.rudra.designsystem.theme.Spacing
import com.rudra.domain.Task
import com.rudra.tasks.state.TaskListUiState
import com.rudra.tasks.viewmodel.TaskListViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

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
                    onComplete = viewModel::completeTask,
                    onUndo = viewModel::undoCompleteTask
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

@Composable
private fun TaskList(
    tasks: List<Task>,
    onComplete: (UUID) -> Unit,
    onUndo: (UUID) -> Unit
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
                onComplete = onComplete,
                onUndo = onUndo
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    onComplete: (UUID) -> Unit,
    onUndo: (UUID) -> Unit
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onComplete(task.id)

                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Task completed",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        onUndo(task.id)
                    }
                }
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {},
        content = {
            TaskCard(task = task)
        }
    )
}

@Composable
private fun TaskCard(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                textDecoration =
                    if (task.isCompleted)
                        TextDecoration.LineThrough
                    else TextDecoration.None
            )

            task.completedAt?.let {
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

