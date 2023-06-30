package com.example.todoapp.data.network.entity

import com.example.todoapp.domain.entity.TodoItem

/**
 * @author Данила Шабанов on 01.07.2023.
 */
internal class BodyResponse(
    val status: String,
    val list: List<TodoItemNetwork>,
    val revision: Int
)