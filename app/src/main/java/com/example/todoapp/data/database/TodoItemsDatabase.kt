package com.example.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.database.converter.LocalDateTimeConverter
import com.example.todoapp.data.database.entity.TodoItemDbEntity

/**
 * @author Данила Шабанов on 24.06.2023.
 */
@Database(
    version = 1,
    entities = [
        TodoItemDbEntity::class
    ]
)
@TypeConverters(LocalDateTimeConverter::class)
internal abstract class TodoItemsDatabase : RoomDatabase() {

    abstract fun getTodoItemsDao(): TodoItemsDao

    companion object {
        private const val DB_NAME = "todo_items.db"

        @Volatile
        private var instance: TodoItemsDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): TodoItemsDatabase {
            return instance
                ?: synchronized(this) {
                    instance ?: Room.databaseBuilder(
                        context.applicationContext,
                        TodoItemsDatabase::class.java,
                        DB_NAME
                    )
                        .build()
                        .also {
                            instance = it
                        }
                }
        }
    }
}
