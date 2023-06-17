package com.example.todoapp.presentation.todoItems

import com.example.todoapp.domain.entity.TodoItem

/**
 * @author Данила Шабанов on 09.06.2023
 */
internal sealed interface Event {

    data class CompleteTask(
        val todoItem: TodoItem,
    ) : Event

    data class DeleteTask(
        val id: String,
    ) : Event

    object Refresh : Event
}

internal sealed interface Effect {

}

internal sealed interface Navigation {

}

internal sealed interface State {

    object EmptyContent : State

    data class Content(
        val screenItems: List<TodoItem>,
    ) : State

    object Error : State
}