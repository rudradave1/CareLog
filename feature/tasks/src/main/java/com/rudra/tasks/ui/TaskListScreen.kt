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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import android.provider.Settings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.snap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.minimumInteractiveComponentSize
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
    Column(
        modifier = Modifier
            .padding(top = Spacing.lg, bottom = Spacing.sm)
            .fillMaxWidth()
    ) {
        Text(
            text = headerText.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(Spacing.xs))
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
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
                val isActive =
                    dismissState.currentValue != SwipeToDismissBoxValue.Settled ||
                        dismissState.targetValue != SwipeToDismissBoxValue.Settled
                val backgroundColor =
                    if (isActive) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        Color.Transparent
                    }
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
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier
                            .alpha(if (isActive) 1f else 0.7f)
                    )
                    Spacer(modifier = Modifier.width(Spacing.xs))
                    Text(
                        text = "Complete",
                        color = contentColor,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .alpha(if (isActive) 1f else 0.7f)
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
    val context = LocalContext.current
    val motionScale = remember(task.id) {
        Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f
        )
    }
    val duration = if (motionScale == 0f) 0 else 200

    val animatedAlpha = animateFloatAsState(
        targetValue = if (task.isCompleted) 0.7f else 1f,
        animationSpec = if (duration == 0) snap() else tween(duration),
        label = "taskAlpha"
    )
    val animatedScale = animateFloatAsState(
        targetValue = if (task.isCompleted) 0.985f else 1f,
        animationSpec = if (duration == 0) snap() else tween(duration),
        label = "taskScale"
    )
    val animatedContainerColor = animateColorAsState(
        targetValue = if (task.isCompleted) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = if (duration == 0) snap() else tween(duration),
        label = "taskContainerColor"
    )

    val completedDateText = task.completedAt?.let {
        it.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(
                scaleX = animatedScale.value,
                scaleY = animatedScale.value,
                alpha = animatedAlpha.value
            ),
        colors = CardDefaults.cardColors(
            containerColor = animatedContainerColor.value
        ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = if (task.isCompleted) 0.dp else 1.dp
            )
    ) {
        Row(
            modifier = Modifier.padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                enabled = !task.isCompleted,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .semantics {
                        contentDescription = if (task.isCompleted) {
                            "Task completed"
                        } else {
                            "Mark task complete"
                        }
                    },
                onCheckedChange = {
                    if (!task.isCompleted) {
                        onComplete(task.id)
                    }
                }
            )

            Spacer(modifier = Modifier.width(Spacing.sm))

            val contentAlpha = if (task.isCompleted) 0.7f else 1f
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(contentAlpha)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration =
                        if (task.isCompleted)
                            TextDecoration.LineThrough
                        else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(Spacing.xs))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.frequency.displayText(),
                        style = MaterialTheme.typography.bodySmall,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                    CategoryChip(category = task.category)
                }

                if (completedDateText != null) {
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = "Completed on $completedDateText",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
