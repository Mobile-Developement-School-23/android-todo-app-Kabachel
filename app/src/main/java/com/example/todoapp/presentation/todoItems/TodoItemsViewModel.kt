package com.example.todoapp.presentation.todoitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.domain.interactor.TodoItemsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author Данила Шабанов on 13.06.2023
 */
internal class TodoItemsViewModel @Inject constructor(
    private val interactor: TodoItemsInteractor,
) : ViewModel() {

    class TodoItemsViewModelFactory @Inject constructor(
        myViewModelProvider: Provider<TodoItemsViewModel>
    ) : ViewModelProvider.Factory {
        private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
            TodoItemsViewModel::class.java to myViewModelProvider
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return providers[modelClass]!!.get() as T
        }
    }

    private val initialState: State by lazy { setInitialState() }
    private val mutableStateFlow = MutableStateFlow(initialState)
    private val stateFlow: StateFlow<State> = mutableStateFlow.asStateFlow()

    val state: StateFlow<State>
        get() = stateFlow

    init {
        createContentState()
    }

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

    val onEvent: (event: Event) -> Unit = {
        handleEvent(it)
    }

    private fun onCompleteTask(event: Event.CompleteTask) {
        runBlocking {
            launch {
                interactor.addTodoItem(
                    event.todoItem.copy(isDone = !event.todoItem.isDone)
                )
            }
        }
    }

    private fun onDeleteTask(event: Event.DeleteTask) {
        runBlocking {
            launch {
                interactor.deleteTodoItemWithId(event.item)
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
