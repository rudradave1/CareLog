package com.rudra.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.tasks.state.TaskListUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<TaskListUiState>(TaskListUiState.Loading)
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        observeTasks()
    }

    fun completeTask(id: UUID) {
        viewModelScope.launch {
            repository.completeTask(id)
        }
    }

    private fun observeTasks() {
        viewModelScope.launch {
            repository.observeTasks()
                .onStart {
                    _uiState.value = TaskListUiState.Loading
                }
                .catch { e ->
                    _uiState.value =
                        TaskListUiState.Error(
                            e.message ?: "Unknown error"
                        )
                }
                .collect { tasks ->
                    _uiState.value =
                        TaskListUiState.Success(tasks)
                }
        }
    }
}
