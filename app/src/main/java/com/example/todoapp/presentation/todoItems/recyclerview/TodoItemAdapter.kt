package com.example.todoapp.presentation.todoitems.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todoapp.R
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.presentation.todoitems.Event

/**
 * @author Данила Шабанов on 10.06.2023
 */
internal class TodoItemAdapter(
    private val onEvent: (Event) -> Unit,
) : ListAdapter<TodoItem, TodoItemViewHolder>(DiffItemCallback()) {

    public override fun getItem(position: Int): TodoItem {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder =
        TodoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.todo_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(
            getItem(position),
            onEvent
        )
    }

    var onTodoItemClick: OnTodoItemClickListener? = null

    interface OnTodoItemClickListener {

        fun onTodoItemClick(todoItemPreview: TodoItem)
    }
}