package com.rudra.carelog.core.database.mapper

import com.rudra.domain.TaskFrequency

object TaskFrequencySerializer {

    fun serialize(frequency: TaskFrequency): String =
        when (frequency) {
            TaskFrequency.Daily -> "DAILY"
            is TaskFrequency.Weekly ->
                "WEEKLY:${frequency.daysOfWeek.joinToString(",")}"
            is TaskFrequency.Monthly ->
                "MONTHLY:${frequency.dayOfMonth}"
        }

    fun deserialize(raw: String): TaskFrequency =
        when {
            raw == "DAILY" ->
                TaskFrequency.Daily

            raw.startsWith("WEEKLY") -> {
                val days =
                    raw.substringAfter(":")
                        .takeIf { it.isNotBlank() }
                        ?.split(",")
                        ?.map { it.toInt() }
                        ?.toSet()
                        ?: emptySet()

                TaskFrequency.Weekly(days)
            }

            raw.startsWith("MONTHLY") -> {
                val day =
                    raw.substringAfter(":").toInt()
                TaskFrequency.Monthly(day)
            }

            else -> TaskFrequency.Daily
        }
}