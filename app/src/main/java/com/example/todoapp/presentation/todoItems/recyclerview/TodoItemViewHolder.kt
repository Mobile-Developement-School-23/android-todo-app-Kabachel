package com.example.todoapp.presentation.todoItems.recyclerview

import android.content.Context
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.presentation.todoItems.Event

/**
 * @author Данила Шабанов on 10.06.2023
 */
internal class TodoItemViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var todoItem: TodoItem

    private val todoItemText: TextView = itemView.findViewById(R.id.checkboxText)
    private val todoItemCheckbox: CheckBox = itemView.findViewById(R.id.checkboxIcon)

    fun onBind(todoItem: TodoItem, onEvent: (Event) -> Unit) {
        this.todoItem = todoItem
        todoItemText.text = todoItem.text
        todoItemCheckbox.isChecked = todoItem.isDone

        setupCrossText(todoItem)
        setupPriority(todoItem)


    }

    private fun setupCrossText(todoItem: TodoItem) {
        if (todoItem.isDone) {
            todoItemText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            todoItemText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    private fun setupPriority(todoItem: TodoItem) {
        when (todoItem.priority) {
            PriorityType.LOW -> todoItemText.text = todoItem.text
            PriorityType.MEDIUM -> todoItemText.text = "↓↓ ${todoItem.text}"
            else -> {
                todoItemText.text = "!! ${todoItem.text}"

                todoItemCheckbox.buttonDrawable?.colorFilter = PorterDuffColorFilter(
                    getThemeAttrColor(todoItemCheckbox.context, R.attr.color_red),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
    }

    private fun getThemeAttrColor(context: Context, @AttrRes colorAttr: Int): Int {
        val array = context.obtainStyledAttributes(null, intArrayOf(colorAttr))
        return try {
            array.getColor(0, 0)
        } finally {
            array.recycle()
        }
    }
}