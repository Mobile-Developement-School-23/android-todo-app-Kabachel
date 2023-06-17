package com.example.todoapp.presentation.todoItems

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.domain.interactor.TodoItemsInteractor
import com.example.todoapp.domain.interactor.TodoItemsInteractorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author Данила Шабанов on 13.06.2023
 */
internal class TodoItemsViewModel(
    private val interactor: TodoItemsInteractor = TodoItemsInteractorImpl(TodoItemsRepositoryImpl()),
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
            is Event.CompleteTask -> {
                onCompleteTask(event)
                createContentState()
            }

            is Event.DeleteTask -> {
                onDeleteTask(event)
                createContentState()
            }

            is Event.Refresh -> {
                createContentState()
            }
        }
    }

    private fun onCompleteTask(event: Event.CompleteTask) {
        runBlocking {
            launch {
                interactor.addTodoItem(
                    TodoItem(
                        id = event.todoItem.id,
                        text = event.todoItem.text,
                        priority = event.todoItem.priority,
                        createdDate = event.todoItem.createdDate,
                        deadlineDate = event.todoItem.deadlineDate,
                        isDone = event.todoItem.isDone
                    )
                )
            }
        }
    }

    private fun onDeleteTask(event: Event.DeleteTask) {
        runBlocking {
            launch {
                interactor.deleteTodoItemWithId(event.id)
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