package com.example.todoapp.data.network.mapper

import com.example.todoapp.data.database.entity.TodoItemDbEntity
import com.example.todoapp.data.database.mapper.TodoItemDbEntityToDomain
import com.example.todoapp.data.network.entity.TodoItemNetwork
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import java.time.LocalDateTime

/**
 * @author Данила Шабанов on 01.07.2023.
 */
internal interface TodoItemNetworkToTodoItemDomain {

    operator fun invoke(todoItem: TodoItemNetwork): TodoItem
}

// TODO() Переписать маппер

internal class TodoItemNetworkToTodoItemDomainImpl : TodoItemNetworkToTodoItemDomain {

    override fun invoke(todoItem: TodoItemNetwork): TodoItem {
        return TodoItem(
            id = todoItem.id,
            text = todoItem.text,
            priority = PriorityType.LOW,
            isDone = false,
            createdDate = LocalDateTime.now(),
        )
    }
}