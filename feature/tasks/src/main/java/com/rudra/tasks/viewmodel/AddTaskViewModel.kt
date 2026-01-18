package com.rudra.tasks.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.domain.Task
import com.rudra.domain.TaskCategory
import com.rudra.domain.TaskFrequency
import com.rudra.tasks.state.AddTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
private const val KEY_TITLE = "add_task_title"

class AddTaskViewModel(
    private val repository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            AddTaskUiState(
                title = savedStateHandle[KEY_TITLE] ?: ""
            )
        )

    val uiState: StateFlow<AddTaskUiState> =
        _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        savedStateHandle[KEY_TITLE] = newTitle

        _uiState.value =
            _uiState.value.copy(title = newTitle)
    }

    fun saveTask() {
        val current = _uiState.value
        if (current.title.isBlank()) return

        viewModelScope.launch {
            _uiState.value =
                current.copy(isSaving = true)

            val taskId = UUID.randomUUID().toString()

            repository.saveTask(
                Task(
                    id = UUID.fromString(taskId),
                    title = current.title,
                    category = TaskCategory.PERSONAL,
                    frequency = TaskFrequency.Daily,
                    startDate = LocalDate.now(),
                    reminderTime = null,
                    lastCompletedAt = null,
                    isActive = true,
                    updatedAt = System.currentTimeMillis()
                )
            )

            _uiState.value =
                current.copy(
                    isSaving = false,
                    taskSaved = true,
                    savedTaskId = taskId
                )

            savedStateHandle.remove<String>(KEY_TITLE)
        }
    }

    fun onTaskConsumed() {
        _uiState.value =
            _uiState.value.copy(
                taskSaved = false,
                savedTaskId = null
            )
    }
}
