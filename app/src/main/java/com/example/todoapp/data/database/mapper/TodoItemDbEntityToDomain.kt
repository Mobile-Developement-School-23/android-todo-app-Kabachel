package com.example.todoapp.data.database.mapper

import com.example.todoapp.data.database.entity.TodoItemDbEntity
import com.example.todoapp.domain.entity.TodoItem
import javax.inject.Inject

/**
 * @author Данила Шабанов on 24.06.2023.
 */
internal interface TodoItemDbEntityToDomain {

    operator fun invoke(todoItem: TodoItemDbEntity): TodoItem
}

internal class TodoItemDbEntityToDomainImpl @Inject constructor() : TodoItemDbEntityToDomain {

    override fun invoke(todoItem: TodoItemDbEntity): TodoItem {
        return TodoItem(
            id = todoItem.id.toString(),
            text = todoItem.text,
            priority = todoItem.priority,
            deadlineDate = todoItem.deadlineDate,
            isDone = todoItem.isDone,
            createdDate = todoItem.createdDate,
            modifiedDate = todoItem.modifiedDate,
        )
    }
}
