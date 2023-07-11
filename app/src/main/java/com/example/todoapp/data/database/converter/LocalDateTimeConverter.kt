package com.example.todoapp.data.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * @author Данила Шабанов on 07.07.2023.
 */
internal class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun localDateTimeToTimestamp(time: LocalDateTime?): Long? {
        return time?.let { it.toEpochSecond(ZoneOffset.UTC) * TO_SECONDS }
    }

    companion object {
        private const val TO_SECONDS = 1000
    }
}