package com.example.todoapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.database.entity.TodoItemDbEntity

/**
 * Dao для Room
 *
 * @author Данила Шабанов on 24.06.2023
 */
@Dao
internal interface TodoItemsDao {

    @Query("SELECT * FROM todo_items")
    suspend fun getAll(): List<TodoItemDbEntity>

    @Insert(entity = TodoItemDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItemDbEntity: TodoItemDbEntity)

    @Query("DELETE FROM todo_items WHERE id = :id")
    suspend fun deleteTodoItem(id: Long): Int
}