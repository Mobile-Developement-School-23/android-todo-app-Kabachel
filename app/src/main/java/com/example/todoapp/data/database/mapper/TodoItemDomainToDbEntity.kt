package com.example.todoapp.data.database.mapper

import com.example.todoapp.data.database.entity.TodoItemDbEntity
import com.example.todoapp.domain.entity.TodoItem

/**
 * @author Данила Шабанов on 24.06.2023.
 */
internal interface TodoItemDomainToDbEntity {

    operator fun invoke(todoItem: TodoItem): TodoItemDbEntity
}

internal class TodoItemDomainToDbEntityImpl : TodoItemDomainToDbEntity {

    override fun invoke(todoItem: TodoItem): TodoItemDbEntity {
        return TodoItemDbEntity(
            id = todoItem.id.toLong(),
            text = todoItem.text,
        )
    }
}