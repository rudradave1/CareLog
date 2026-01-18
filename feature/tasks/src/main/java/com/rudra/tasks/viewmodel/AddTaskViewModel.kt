package com.rudra.tasks.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.carelog.core.database.domain.enums.TaskCategory
import com.rudra.carelog.core.database.domain.enums.TaskFrequency
import com.rudra.carelog.core.database.domain.model.Task
import com.rudra.carelog.core.database.repository.TaskRepository
import com.rudra.tasks.state.AddTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class AddTaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> =
        _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.value =
            _uiState.value.copy(title = newTitle)
    }

    fun saveTask() {
        val current = _uiState.value
        if (current.title.isBlank()) return

        viewModelScope.launch {
            _uiState.value =
                current.copy(isSaving = true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                repository.saveTask(
                    Task(
                        id = UUID.randomUUID(),
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
            } else {
                //todo add  for older versions
                /*repository.saveTask(
                    Task(
                        id = UUID.randomUUID(),
                        title = current.title,
                        category = TaskCategory.PERSONAL,
                        frequency = TaskFrequency.Daily,
                        startDate = LocalDate.now(),
                        reminderTime = null,
                        lastCompletedAt = null,
                        isActive = true,
                        updatedAt = System.currentTimeMillis()
                    )
                )*/
            }

            _uiState.value =
                current.copy(isSaving = false)
        }
    }
}
