package com.rudra.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rudra.carelog.core.database.repository.TaskSyncRepository
import com.rudra.common.preferences.UserPreferences

class SettingsViewModelFactory(
    private val userPreferences: UserPreferences,
    private val taskSyncRepository: TaskSyncRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                SettingsViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                userPreferences,
                taskSyncRepository
            ) as T
        }
        throw IllegalArgumentException(
            "Unknown ViewModel"
        )
    }
}
