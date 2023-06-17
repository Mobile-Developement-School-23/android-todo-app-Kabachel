package com.example.todoapp.presentation.todoItems.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.presentation.todoItems.recyclerview.domain.CommonCallbackImpl
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Данила Шабанов on 10.06.2023
 */
internal class TodoItemAdapter : RecyclerView.Adapter<TodoItemViewHolder>() {

    var items = MutableStateFlow(listOf<TodoItem>())
        set(value) {
            val callback = CommonCallbackImpl(
                oldItems = field.value,
                newItems = value.value,
                { oldItem: TodoItem, newItem -> oldItem.id == newItem.id })
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }

    var onTodoItemClick: OnTodoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoItemViewHolder(
            layoutInflater.inflate(
                R.layout.todo_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.value.size

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(items.value[position])

        holder.itemView.findViewById<CheckBox>(R.id.checkboxIcon).setOnClickListener {
            onTodoItemClick?.onTodoItemClick(items.value[position])
            holder.onBind(items.value[position])
        }
    }

    interface OnTodoItemClickListener {

        fun onTodoItemClick(todoItemPreview: TodoItem)
    }
}