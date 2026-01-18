package com.rudra.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rudra.carelog.core.database.repository.TaskRepository

class AddTaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(
                AddTaskViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return AddTaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
