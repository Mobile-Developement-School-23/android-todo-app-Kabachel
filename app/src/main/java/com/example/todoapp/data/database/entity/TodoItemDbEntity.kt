package com.example.todoapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.database.serializer.LocalDateTimeSerializer
import com.example.todoapp.domain.entity.PriorityType
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Entity TodoItem для Room
 *
 * @author Данила Шабанов on 24.06.2023
 */
@Serializable
@Entity(tableName = "todo_items")
internal data class TodoItemDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "priority")
    val priority: PriorityType,

    @Serializable(with = LocalDateTimeSerializer::class)
    @ColumnInfo(name = "deadline_date")
    val deadlineDate: LocalDateTime? = null,

    @ColumnInfo(name = "is_done")
    val isDone: Boolean = false,

    @Serializable(with = LocalDateTimeSerializer::class)
    @ColumnInfo(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Serializable(with = LocalDateTimeSerializer::class)
    @ColumnInfo(name = "modified_date")
    var modifiedDate: LocalDateTime = LocalDateTime.now(),
)