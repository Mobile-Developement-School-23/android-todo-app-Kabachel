package com.example.todoapp.domain.entity

import java.time.LocalDateTime
import java.util.Date

/**
 * @author Данила Шабанов on 09.06.2023
 */
internal data class TodoItem(
    var id: String,
    var text: String,
    var priority: PriorityType,
    var deadlineDate: Date? = null,
    var isDone: Boolean = false,
    val createdDate: LocalDateTime,
    var modifiedDate: Date? = null,
)
