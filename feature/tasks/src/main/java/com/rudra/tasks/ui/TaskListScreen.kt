package com.rudra.tasks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.rudra.designsystem.components.CategoryChip
import com.rudra.designsystem.components.EmptyState
import com.rudra.designsystem.components.LoadingState
import com.rudra.designsystem.theme.LocalSnackbarHostState
import com.rudra.designsystem.theme.Spacing
import com.rudra.designsystem.util.displayText
import com.rudra.domain.Task
import com.rudra.tasks.state.TaskListUiState
import com.rudra.tasks.viewmodel.TaskListEvent
import com.rudra.tasks.viewmodel.TaskListViewModel
import java.util.UUID
import java.time.format.DateTimeFormatter

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current

    // One-off events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is TaskListEvent.TaskCompleted -> {
                    val result = snackbarHostState.showSnackbar(
                        message = "Task marked as complete",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Long
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.undoCompleteTask(event.taskId)
                    }
                }
            }
        }
    }

    when (uiState) {
        TaskListUiState.Loading -> LoadingState()

        is TaskListUiState.Success -> {
            TaskListContent(
                tasks = (uiState as TaskListUiState.Success).tasks,
                onComplete = viewModel::completeTask
            )
        }

        is TaskListUiState.Error -> {
            EmptyState(
                title = "Something went wrong",
                message = (uiState as TaskListUiState.Error).message
            )
        }
    }
}

@Composable
private fun TaskListContent(
    tasks: List<Task>,
    onComplete: (UUID) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyState(
            title = "No tasks yet",
            message = "Tap + to add your first task."
        )
        return
    }

    val activeTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Spacing.md)
    ) {
        TaskSections(
            activeTasks = activeTasks,
            completedTasks = completedTasks,
            onComplete = onComplete
        )
    }
}
private fun LazyListScope.TaskSections(
    activeTasks: List<Task>,
    completedTasks: List<Task>,
    onComplete: (UUID) -> Unit
) {
    if (activeTasks.isNotEmpty()) {
        item {
            SectionHeader(
                title = "Active",
                count = activeTasks.size
            )
        }

        items(activeTasks, key = { it.id }) { task ->
            TaskItem(task = task, onComplete = onComplete)
            Spacer(modifier = Modifier.height(Spacing.sm))
        }
    }

    if (completedTasks.isNotEmpty()) {
        item {
            Spacer(modifier = Modifier.height(Spacing.lg))
            SectionHeader(
                title = "Completed",
                count = completedTasks.size
            )
        }

        items(completedTasks, key = { it.id }) { task ->
            TaskItem(task = task, onComplete = {})
            Spacer(modifier = Modifier.height(Spacing.sm))
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    count: Int? = null
) {
    val headerText = if (count != null) {
        "$title ($count)"
    } else {
        title
    }

    Text(
        text = headerText.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(
                top = Spacing.lg,
                bottom = Spacing.sm
            )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    onComplete: (UUID) -> Unit,
) {
    if (task.isCompleted) {
        TaskCard(task = task, onComplete = {})
    } else {
        val dismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                if (value == SwipeToDismissBoxValue.EndToStart) {
                    onComplete(task.id)
                    true
                } else false
            }
        )

        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val backgroundColor =
                    MaterialTheme.colorScheme.primaryContainer
                val contentColor =
                    MaterialTheme.colorScheme.onPrimaryContainer

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                        .padding(horizontal = Spacing.md),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Complete",
                        color = contentColor,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            content = {
                TaskCard(task = task) {
                    onComplete(task.id)
                }
            }
        )
    }
}

@Composable
fun TaskCard(
    task: Task,
    onComplete: (UUID) -> Unit
) {
    val completedDateText = task.completedAt?.let {
        it.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors =
            if (task.isCompleted)
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            else CardDefaults.cardColors(),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
    ) {
        Row(
            modifier = Modifier.padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                enabled = !task.isCompleted,
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

                Spacer(modifier = Modifier.height(Spacing.xs))

                Text(
                    text = task.frequency.displayText(),
                    style = MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                completedDateText?.let { dateText ->
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = "Completed on $dateText",
                        style =
                            MaterialTheme.typography.bodySmall,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.sm))

                CategoryChip(category = task.category)
            }
        }
    }
}
