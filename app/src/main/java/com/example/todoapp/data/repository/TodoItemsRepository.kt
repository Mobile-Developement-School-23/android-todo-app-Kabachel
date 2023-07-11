package com.example.todoapp.data.repository

import com.example.todoapp.data.database.TodoItemsDao
import com.example.todoapp.data.database.mapper.TodoItemDbEntityToDomain
import com.example.todoapp.data.database.mapper.TodoItemDomainToDbEntity
import com.example.todoapp.data.network.NetworkApi
import com.example.todoapp.data.network.mapper.TodoItemNetworkToTodoItemDomain
import com.example.todoapp.domain.entity.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Репозиторий для работы со списками дел
 *
 * @author Данила Шабанов on 09.06.2023
 */
internal interface TodoItemsRepository {

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
     * @param item дело которое нужно удалить
     */
    suspend fun deleteTodoItemWithId(item: TodoItem)

    /**
     * Получить размер списка дел
     */
    suspend fun getTodoItemSize(): Int
}

internal class TodoItemsRepositoryImpl @Inject constructor(
    private val dao: TodoItemsDao,
    private val api: NetworkApi,
    private val dbEntityToDomain: TodoItemDbEntityToDomain,
    private val domainToDbEntity: TodoItemDomainToDbEntity,
    private val networkToDomain: TodoItemNetworkToTodoItemDomain,
) : TodoItemsRepository {

    override suspend fun getTodoItems(): List<TodoItem> =
        withContext(Dispatchers.IO) {
//            val request = api.getTodoItems()
//
//            if (request.isFailure) {
//                val items = dao.getAll().map { dbEntityToDomain(it) }
//                println("---> elements = $items")
//                items
//            } else {
//                request.getOrThrow().list.map { networkToDomain(it) }
//            }

            dao.getAll().map { dbEntityToDomain(it) }
        }

    override suspend fun addTodoItem(todoItem: TodoItem) =
        withContext(Dispatchers.IO) {
            dao.insertTodoItem(domainToDbEntity(todoItem))
        }

    override suspend fun deleteTodoItemWithId(item: TodoItem) {
        withContext(Dispatchers.IO) {
            println("--->deleted = ${dao.deleteTodoItem(item.id.toLong())}")
        }
    }

    override suspend fun getTodoItemSize(): Int = dao.getAll().size
}
