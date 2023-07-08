package com.example.todoapp.di

import com.example.todoapp.presentation.addtodoitem.AddTodoItemViewModel
import com.example.todoapp.presentation.todoitems.TodoItemsViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * @author Данила Шабанов on 08.07.2023.
 */
@Singleton
@Component(
    modules = [
        TodoAppBindsModule::class,
        TodoAppDatabaseModule::class,
        TodoAppNetworkModule::class,
        TodoAppProvideContextModule::class,
    ]
)
internal interface TodoAppComponent {
    fun todoItemsViewModelsFactory(): TodoItemsViewModel.TodoItemsViewModelFactory
    fun addTodoItemViewModelFactory(): AddTodoItemViewModel.AddTodoItemViewModelFactory
}
