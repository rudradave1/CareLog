package com.rudra.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rudra.common.worker.ReminderWorker
import com.rudra.tasks.viewmodel.AddTaskViewModel

@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel,
    onTaskSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.taskSaved) {
        if (uiState.taskSaved) {
        //reminder worker got no access here
            ReminderWorker.schedule(context)
            onTaskSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("Task title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveTask()
                onTaskSaved()
            },
            enabled = !uiState.isSaving
        ) {
            Text("Save")
        }
    }
}
