package com.example.todoapp.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.FloatRange
import androidx.annotation.IntRange

/**
 * @author Данила Шабанов on 25.06.2023.
 */
fun Context.dpToPx(@FloatRange(from = 0.0) value: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)

fun Context.dpToPx(@IntRange(from = 0) value: Int) =
    dpToPx(value.toFloat()).toInt()

fun View.dpToPx(@IntRange(from = 0) value: Int) =
    context.dpToPx(value)

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}