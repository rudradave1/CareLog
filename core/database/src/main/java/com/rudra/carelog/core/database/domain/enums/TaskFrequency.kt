package com.rudra.carelog.core.database.domain.enums

sealed class TaskFrequency {
    data object Daily : TaskFrequency()
    data class Weekly(val daysOfWeek: Set<Int>) : TaskFrequency()
    data class Monthly(val dayOfMonth: Int) : TaskFrequency()
}