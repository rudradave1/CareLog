package com.rudra.carelog.core.database.converter


import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class Converters {

    @TypeConverter
    fun fromUuid(value: UUID): String = value.toString()

    @TypeConverter
    fun toUuid(value: String): UUID = UUID.fromString(value)

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? =
        date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? =
        value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? =
        time?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? =
        value?.let { LocalTime.parse(it) }
}