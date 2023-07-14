package com.example.todoapp.presentation.todoitems

import com.example.todoapp.domain.entity.TodoItem

/**
 * @author Данила Шабанов on 09.06.2023
 */
internal sealed interface Event {

    data class CompleteTask(
        val todoItem: TodoItem,
    ) : Event

    data class DeleteTask(
        val item: TodoItem,
    ) : Event

    object Refresh : Event

    object AddTodoItem : Event
}

internal sealed interface Navigation {

    object AddTodoItem : Navigation
}

internal sealed interface State {

    object EmptyContent : State

    data class Content(
        val screenItems: List<TodoItem>,
    ) : State
}