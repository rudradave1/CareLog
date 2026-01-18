package com.rudra.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.domain.Task
import com.rudra.tasks.state.TaskListUiState
import com.rudra.tasks.viewmodel.TaskListViewModel

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onAddClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
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
                val tasks =
                    (uiState as TaskListUiState.Success).tasks
                TaskList(tasks)
            }

            is TaskListUiState.Error -> {
                val message =
                    (uiState as TaskListUiState.Error).message
                Text(
                    text = message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun TaskList(
    tasks: List<Task>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(tasks) { task ->
            TaskItem(task)
            Spacer(modifier = Modifier.height(8.dp))
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
