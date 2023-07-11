package com.example.todoapp.data.network.mapper

import com.example.todoapp.data.network.entity.TodoItemNetwork
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * @author Данила Шабанов on 01.07.2023.
 */
internal interface TodoItemNetworkToTodoItemDomain {

    operator fun invoke(todoItem: TodoItemNetwork): TodoItem
}

// TODO() Переписать маппер

internal class TodoItemNetworkToTodoItemDomainImpl @Inject constructor() :
    TodoItemNetworkToTodoItemDomain {

    override fun invoke(todoItem: TodoItemNetwork): TodoItem {
        return TodoItem(
            id = todoItem.id,
            text = todoItem.text,
            priority = PriorityType.LOW,
            isDone = false,
            createdDate = LocalDateTime.now(),
            modifiedDate = LocalDateTime.now(),
        )
    }
}
