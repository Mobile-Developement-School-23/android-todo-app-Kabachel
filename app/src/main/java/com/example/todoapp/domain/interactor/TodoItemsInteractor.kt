package com.example.todoapp.domain.interactor

import com.example.todoapp.data.database.Dependencies
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.domain.entity.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Интерактор для взаимодействия с делами
 *
 * @author Данила Шабанов on 09.06.2023
 */
internal interface TodoItemsInteractor {

    /**
     * Получить список дел
     */
    suspend fun getTodoItems(): List<TodoItem>

    /**
     * Добавить дело
     *
     * @param todoItem дело которое следует добавить
     */
    suspend fun addTodoItem(todoItem: TodoItem)

    /**
     * Удалить дело
     *
     * @param id дела которое нужно удалить
     */
    suspend fun deleteTodoItemWithId(item: TodoItem)

    /**
     * Получить размер списка дел
     */
    suspend fun getTodoItemSize()
}

internal class TodoItemsInteractorImpl(
    private val todoItemsRepository: TodoItemsRepository,
) : TodoItemsInteractor {

    override suspend fun getTodoItems(): List<TodoItem> =
        withContext(Dispatchers.IO) {
            todoItemsRepository.getTodoItems()
        }

    override suspend fun addTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            todoItemsRepository.addTodoItem(todoItem)
        }

    override suspend fun deleteTodoItemWithId(item: TodoItem) {
        withContext(Dispatchers.IO) {
            todoItemsRepository.deleteTodoItemWithId(item)
        }
    }

    override suspend fun getTodoItemSize() {
        withContext(Dispatchers.IO) {
            todoItemsRepository.getTodoItemSize()
        }
    }
}