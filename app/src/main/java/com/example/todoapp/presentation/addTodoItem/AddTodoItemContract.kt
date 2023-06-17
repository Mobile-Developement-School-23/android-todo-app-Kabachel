package com.example.todoapp.presentation.addTodoItem

import com.example.todoapp.domain.entity.TodoItem

internal sealed interface Event {

    data class AddTodoItemPreview(
        val todoItemPreview: TodoItem,
    ) : Event
}

internal sealed interface State {

    object EmptyContent : State

    data class Content(
        val screenItems: List<TodoItem>,
    ) : State

    object Error : State
}