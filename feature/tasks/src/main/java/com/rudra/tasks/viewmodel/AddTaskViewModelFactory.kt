package com.rudra.tasks.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.rudra.carelog.core.database.repository.TaskRepository

class AddTaskViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: TaskRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (
            modelClass.isAssignableFrom(
                AddTaskViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return AddTaskViewModel(
                repository = repository,
                savedStateHandle = handle
            ) as T
        }
        throw IllegalArgumentException(
            "Unknown ViewModel"
        )
    }
}
