package com.rudra.tasks.state

import com.rudra.domain.TaskFrequency

data class AddTaskUiState(
    val title: String = "",
    val frequency: TaskFrequency = TaskFrequency.Daily,
    val isSaving: Boolean = false,
    val taskSaved: Boolean = false,
    val savedTaskId: String? = null,
    val error: String? = null
)