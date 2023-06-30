package com.example.todoapp.data.database.mapper

import com.example.todoapp.data.database.entity.TodoItemDbEntity
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import java.time.LocalDateTime

/**
 * @author Данила Шабанов on 24.06.2023.
 */
internal interface TodoItemDbEntityToDomain {

    operator fun invoke(todoItem: TodoItemDbEntity): TodoItem
}

// TODO() Переписать маппер

internal class TodoItemDbEntityToDomainImpl : TodoItemDbEntityToDomain {

    override fun invoke(todoItem: TodoItemDbEntity): TodoItem {
        return TodoItem(
            id = todoItem.id.toString(),
            text = todoItem.text,
            priority = PriorityType.LOW,
            isDone = false,
            createdDate = LocalDateTime.now(),
        )
    }
}