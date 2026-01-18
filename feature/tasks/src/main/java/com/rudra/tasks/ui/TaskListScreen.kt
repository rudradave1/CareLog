package com.rudra.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rudra.designsystem.components.EmptyState
import com.rudra.designsystem.components.LoadingState
import com.rudra.designsystem.components.TaskCard
import com.rudra.designsystem.theme.CareLogScaffold
import com.rudra.domain.Task
import com.rudra.tasks.state.TaskListUiState
import com.rudra.tasks.viewmodel.TaskListViewModel

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel
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
            TaskList(
                tasks = (uiState as TaskListUiState.Success).tasks
            )
        }

        is TaskListUiState.Error -> {
            Text(
                text = (uiState as TaskListUiState.Error).message,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}



@Composable
fun TaskList(
    tasks: List<Task>
) {
    LazyColumn {
        items(tasks) { task ->
            TaskCard(
                title = task.title,
                subtitle = task.category.name
            )
        }
    }
}


@Composable
private fun TaskItem(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = task.category.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
