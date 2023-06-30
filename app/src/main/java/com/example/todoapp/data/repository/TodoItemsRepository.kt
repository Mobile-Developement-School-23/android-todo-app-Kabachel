package com.example.todoapp.data.repository

import android.icu.util.UniversalTimeScale.toLong
import android.media.CamcorderProfile.getAll
import com.example.todoapp.data.database.TodoItemsDao
import com.example.todoapp.data.database.mapper.TodoItemDbEntityToDomain
import com.example.todoapp.data.database.mapper.TodoItemDbEntityToDomainImpl
import com.example.todoapp.data.database.mapper.TodoItemDomainToDbEntity
import com.example.todoapp.data.database.mapper.TodoItemDomainToDbEntityImpl
import com.example.todoapp.data.network.NetworkApi
import com.example.todoapp.data.network.NetworkApiImpl
import com.example.todoapp.data.network.entity.TodoItemNetwork
import com.example.todoapp.data.network.mapper.TodoItemNetworkToTodoItemDomain
import com.example.todoapp.data.network.mapper.TodoItemNetworkToTodoItemDomainImpl
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

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
    suspend fun getTodoItemSize() = list.size
}

internal class TodoItemsRepositoryImpl(
    private val dao: TodoItemsDao,
    private val api: NetworkApi = NetworkApiImpl(),
    private val dbEntityToDomain: TodoItemDbEntityToDomain = TodoItemDbEntityToDomainImpl(),
    private val domainToDbEntity: TodoItemDomainToDbEntity = TodoItemDomainToDbEntityImpl(),
    private val networkToDomain: TodoItemNetworkToTodoItemDomain = TodoItemNetworkToTodoItemDomainImpl(),
) : TodoItemsRepository {

    override suspend fun getTodoItems(): List<TodoItem> =
        withContext(Dispatchers.IO) {
            val request = api.getTodoItems()

            if (request.isFailure) {
                val items = dao.getAll().map { dbEntityToDomain(it) }
                println("---> elements = $items")
                items
            } else {
                request.getOrThrow().list.map { networkToDomain(it) }
            }
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
}

private val list = mutableListOf(
    TodoItem(
        "0",
        "Task-0ddddddddddddddd afasdfffffffffffffffffff asdggggggggggggggggggggggggggg adgggggggggggggggggggggggggggggggggggdsa asdggggggggggggggggggggggggggggggggg 2314444444444444444444412 3124444443124412335 213451111111111111111111",
        PriorityType.LOW,
        createdDate = LocalDateTime.now()
    ),
    TodoItem("1", "Task-1", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("2", "Task-2", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("3", "Task-3", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("4", "Task-4", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("5", "Task-5", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("6", "Task-6", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("7", "Task-7", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("8", "Task-8", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("9", "Task-9", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("10", "Task-10", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("11", "Task-11", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("12", "Task-12", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("13", "Task-13", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("14", "Task-14", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("15", "Task-15", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("16", "Task-16", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("17", "Task-17", PriorityType.HIGH, createdDate = LocalDateTime.now()),
    TodoItem("18", "Task-18", PriorityType.MEDIUM, createdDate = LocalDateTime.now()),
    TodoItem("19", "Task-19", PriorityType.HIGH, createdDate = LocalDateTime.now()),

    )