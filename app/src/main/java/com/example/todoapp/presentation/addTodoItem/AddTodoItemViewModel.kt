package com.example.todoapp.presentation.addTodoItem

import androidx.lifecycle.ViewModel
import com.example.todoapp.MainActivity
import com.example.todoapp.data.database.Dependencies
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.domain.interactor.TodoItemsInteractor
import com.example.todoapp.domain.interactor.TodoItemsInteractorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class AddTodoItemViewModel(
    private val interactor: TodoItemsInteractor = TodoItemsInteractorImpl(Dependencies.repository),
) : ViewModel() {

    private val initialState: State by lazy { setInitialState() }
    private val mutableStateFlow = MutableStateFlow(initialState)
    private val stateFlow: StateFlow<State> = mutableStateFlow.asStateFlow()

    val state: State
        get() = stateFlow.value

    private fun setInitialState(): State = State.EmptyContent

    private fun setState(reducer: State.() -> State) {
        val newState = mutableStateFlow.value.reducer()
        mutableStateFlow.value = newState
    }

    fun handleEvent(event: Event) {
        when (event) {
            is Event.AddTodoItemPreview -> {
                onAddTodoItemPreview(event)
                createContentState()
            }
        }
    }

    private fun onAddTodoItemPreview(event: Event.AddTodoItemPreview) {
        runBlocking {
            launch {
                interactor.addTodoItem(
                    TodoItem(
                        id = event.todoItemPreview.id,
                        text = event.todoItemPreview.text,
                        priority = event.todoItemPreview.priority,
                        deadlineDate = event.todoItemPreview.deadlineDate,
                        isDone = event.todoItemPreview.isDone,
                        createdDate = event.todoItemPreview.createdDate,
                        modifiedDate = null,
                    )
                )
            }
        }
    }

    private fun createContentState() {
        runBlocking {
            launch {
                val state = State.Content(interactor.getTodoItems())
                setState { state }
            }
        }
    }


}