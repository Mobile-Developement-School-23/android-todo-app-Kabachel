package com.example.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.database.entity.TodoItemDbEntity
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl

/**
 * @author Данила Шабанов on 24.06.2023.
 */
@Database(
    version = 1,
    entities = [
        TodoItemDbEntity::class
    ]
)
internal abstract class TodoItemsDatabase : RoomDatabase() {

    abstract fun getTodoItemsDao(): TodoItemsDao
}

internal object Dependencies {
    private const val DB_NAME = "todo_items.db"
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: TodoItemsDatabase by lazy {
        Room.databaseBuilder(applicationContext, TodoItemsDatabase::class.java, DB_NAME).build()
    }

    val repository: TodoItemsRepository by lazy { TodoItemsRepositoryImpl(appDatabase.getTodoItemsDao()) }
}