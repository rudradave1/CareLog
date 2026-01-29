package com.rudra.designsystem.util


import com.rudra.domain.TaskFrequency

fun TaskFrequency.displayText(): String {
    return when (this) {
        TaskFrequency.Daily -> "Daily"
        is TaskFrequency.Weekly -> "Weekly"
        is TaskFrequency.Monthly -> "Monthly"
    }
}