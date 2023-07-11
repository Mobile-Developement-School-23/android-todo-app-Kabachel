package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.database.TodoItemsDao
import com.example.todoapp.data.database.TodoItemsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Данила Шабанов on 08.07.2023.
 */
@Module
internal object TodoAppDatabaseModule {

    @Provides
    @Singleton
    fun provideTodoItemsDatabase(
        context: Context,
    ): TodoItemsDatabase = TodoItemsDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideTodoItemsDao(
        database: TodoItemsDatabase,
    ): TodoItemsDao = database.getTodoItemsDao()
}
