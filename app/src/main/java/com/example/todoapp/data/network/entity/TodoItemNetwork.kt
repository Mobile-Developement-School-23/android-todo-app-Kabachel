package com.example.todoapp.data.network.entity

import com.example.todoapp.domain.entity.PriorityType
import java.time.LocalDateTime
import java.util.Date

/**
 * @author Данила Шабанов on 01.07.2023.
 */
class TodoItemNetwork(
    var id: String,
    var text: String,
    var importance: String,
    var deadline: String,
    var done: Boolean = false,
    val created_at: String,
    var changed_at: String,
)