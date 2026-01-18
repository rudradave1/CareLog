package com.rudra.tasks.state
data class AddTaskUiState(
    val title: String = "",
    val isSaving: Boolean = false,
    val taskSaved: Boolean = false,
    val error: String? = null
)