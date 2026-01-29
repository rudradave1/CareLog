package com.rudra.carelog.core.database.mapper


import com.rudra.domain.TaskFrequency

fun encodeFrequency(
    frequency: TaskFrequency
): Pair<String, String?> {
    return when (frequency) {
        TaskFrequency.Daily ->
            "DAILY" to null

        is TaskFrequency.Weekly ->
            "WEEKLY" to frequency.daysOfWeek.joinToString(",")

        is TaskFrequency.Monthly ->
            "MONTHLY" to frequency.dayOfMonth.toString()
    }
}

fun decodeFrequency(
    type: String,
    data: String?
): TaskFrequency {
    return when (type) {
        "DAILY" -> TaskFrequency.Daily

        "WEEKLY" ->
            TaskFrequency.Weekly(
                data?.split(",")
                    ?.map { it.toInt() }
                    ?.toSet()
                    ?: emptySet()
            )

        "MONTHLY" ->
            TaskFrequency.Monthly(
                data?.toInt() ?: 1
            )

        else -> TaskFrequency.Daily
    }
}