package com.rudra.tasks.state

import com.rudra.carelog.core.database.domain.model.Task

sealed interface TaskListUiState {
    data object Loading : TaskListUiState
    data class Success(
        val tasks: List<Task>
    ) : TaskListUiState

    data class Error(
        val message: String
    ) : TaskListUiState
}
