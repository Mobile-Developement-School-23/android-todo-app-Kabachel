package com.example.todoapp.presentation.addtodoitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.domain.interactor.TodoItemsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Provider

internal class AddTodoItemViewModel @Inject constructor(
    private val interactor: TodoItemsInteractor,
) : ViewModel() {

    class AddTodoItemViewModelFactory @Inject constructor(
        myViewModelProvider: Provider<AddTodoItemViewModel>
    ) : ViewModelProvider.Factory {
        private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
            AddTodoItemViewModel::class.java to myViewModelProvider
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return providers[modelClass]!!.get() as T
        }
    }

    private val initialState: State by lazy { setInitialState() }
    private val mutableStateFlow = MutableStateFlow(initialState)

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
                        modifiedDate = event.todoItemPreview.modifiedDate,
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
