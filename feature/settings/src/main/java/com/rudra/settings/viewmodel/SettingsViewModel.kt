package com.rudra.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.common.preferences.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val remindersEnabled: StateFlow<Boolean> =
        userPreferences.remindersEnabled
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = true
            )

    fun onRemindersToggled(
        enabled: Boolean
    ) {
        viewModelScope.launch {
            userPreferences.setRemindersEnabled(enabled)
        }
    }
}
