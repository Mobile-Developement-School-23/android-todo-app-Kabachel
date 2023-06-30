package com.example.todoapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity TodoItem для Room
 *
 * @author Данила Шабанов on 24.06.2023
 */
@Entity(tableName = "todo_items")
internal data class TodoItemDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "text") val text: String,
)