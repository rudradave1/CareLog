package com.rudra.tasks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rudra.common.worker.scheduleReminder
import com.rudra.designsystem.theme.CareLogScaffold
import com.rudra.designsystem.theme.CareLogTextField
import com.rudra.designsystem.theme.PrimaryButton
import com.rudra.designsystem.theme.Spacing
import com.rudra.tasks.viewmodel.AddTaskViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Text

@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel,
    remindersEnabled: Boolean,
    onTaskSaved: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(uiState.savedTaskId) {
        val taskId = uiState.savedTaskId

        if (taskId != null && remindersEnabled) {
            scheduleReminder(
                context = context,
                taskId = taskId,
                title = uiState.title
            )
        }

        if (taskId != null) {
            onTaskSaved()
            viewModel.onTaskConsumed()
        }
    }

    CareLogScaffold(
        title = "Add Task",
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.md),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                CareLogTextField(
                    value = uiState.title,
                    label = "Task title",
                    onValueChange = viewModel::onTitleChange,
                    supportingText = "Keep it short and actionable.",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (uiState.title.isNotBlank()) {
                                viewModel.saveTask()
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.height(Spacing.lg))

                FrequencySelector(
                    selected = uiState.frequency,
                    onChange = viewModel::onFrequencyChange
                )

                Spacer(modifier = Modifier.height(Spacing.xs))

                Text(
                    text = "Start with Daily. You can refine this later.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            PrimaryButton(
                text = "Save task",
                enabled = uiState.title.isNotBlank() && !uiState.isSaving,
                onClick = viewModel::saveTask,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}
