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

/**
 * @author Данила Шабанов on 10.06.2023
 */
internal class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val todoItemText: TextView = itemView.findViewById(R.id.checkboxText)
    private val todoItemCheckbox: CheckBox = itemView.findViewById(R.id.checkboxIcon)

    fun onBind(todoItemPreview: TodoItem) {
        todoItemText.text = todoItemPreview.text
        todoItemCheckbox.isChecked = todoItemPreview.isDone

        if (todoItemPreview.isDone) {
            todoItemText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            todoItemText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        when (todoItemPreview.priority) {
            PriorityType.LOW -> todoItemText.text = todoItemPreview.text
            PriorityType.MEDIUM -> todoItemText.text = "↓↓ ${todoItemPreview.text}"
            else -> {
                todoItemText.text = "!! ${todoItemPreview.text}"

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