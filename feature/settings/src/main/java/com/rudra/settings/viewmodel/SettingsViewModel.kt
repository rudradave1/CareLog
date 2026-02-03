package com.rudra.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.carelog.core.database.repository.SyncResult
import com.rudra.carelog.core.database.repository.TaskSyncRepository
import com.rudra.common.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferences: UserPreferences,
    private val taskSyncRepository: TaskSyncRepository
) : ViewModel() {

    val remindersEnabled: StateFlow<Boolean> =
        userPreferences.remindersEnabled
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = true
            )

    val lastSyncTime: StateFlow<Long?> =
        taskSyncRepository.lastSyncTime
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    val pendingCount: StateFlow<Int> =
        taskSyncRepository.observePendingCount()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    private val _syncing =
        MutableStateFlow(false)
    val syncing: StateFlow<Boolean> =
        _syncing.asStateFlow()

    private val _syncError =
        MutableStateFlow<String?>(null)
    val syncError: StateFlow<String?> =
        _syncError.asStateFlow()

    fun onRemindersToggled(
        enabled: Boolean
    ) {
        viewModelScope.launch {
            userPreferences.setRemindersEnabled(enabled)
        }
    }

    fun onSyncNow() {
        if (_syncing.value) return

        viewModelScope.launch {
            _syncing.value = true
            _syncError.value = null

            when (val result = taskSyncRepository.syncNow()) {
                is SyncResult.Success -> {
                    _syncError.value = null
                }
                is SyncResult.Error -> {
                    _syncError.value = result.message
                }
            }

            _syncing.value = false
        }
    }
}
