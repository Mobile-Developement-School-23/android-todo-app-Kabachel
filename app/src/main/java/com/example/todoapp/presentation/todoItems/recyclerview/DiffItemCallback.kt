package com.example.todoapp.presentation.todoitems.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.domain.entity.TodoItem

/**
 * @author Данила Шабанов on 10.06.2023
 */
internal class DiffItemCallback : DiffUtil.ItemCallback<TodoItem>() {

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
        oldItem.id == newItem.id
}