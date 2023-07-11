package com.example.todoapp.presentation.todoitems.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.utils.dpToPx

/**
 * @author Данила Шабанов on 25.06.2023.
 */
internal class SwipeItemCallback(
    private val context: Context,
    private val onSwipeLeft: (position: Int) -> Unit,
    private val onSwipeRight: (position: Int) -> Unit,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val rightSwipePaint = Paint().apply {
        color = context.getColor(R.color.color_light_green)
    }
    private val leftSwipePaint = Paint().apply {
        color = context.getColor(R.color.color_light_red)
    }
    private val whitePaint = Paint().apply {
        colorFilter = PorterDuffColorFilter(
            context.getColor(R.color.color_light_white),
            PorterDuff.Mode.SRC_IN
        )
    }
    private val acceptIcon =
        AppCompatResources.getDrawable(context, R.drawable.ic_check)?.toBitmap()
    private val deleteIcon =
        AppCompatResources.getDrawable(context, R.drawable.ic_delete)?.toBitmap()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> onSwipeLeft(viewHolder.adapterPosition)
            ItemTouchHelper.RIGHT -> onSwipeRight(viewHolder.adapterPosition)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            if (dX > 0) {
                c.drawRect(
                    itemView.left.toFloat(), itemView.top.toFloat(), dX,
                    itemView.bottom.toFloat(), rightSwipePaint
                )
                if (acceptIcon != null) {
                    c.drawBitmap(
                        acceptIcon,
                        itemView.left.toFloat() - context.dpToPx(40) + dX,
                        itemView.top.toFloat() + (itemView.bottom.toFloat() -
                                itemView.top.toFloat() - acceptIcon.height) / 2,
                        whitePaint
                    )
                }
            } else {
                c.drawRect(
                    itemView.right.toFloat() + dX, itemView.top.toFloat(),
                    itemView.right.toFloat(), itemView.bottom.toFloat(), leftSwipePaint
                )
                if (deleteIcon != null) {
                    c.drawBitmap(
                        deleteIcon,
                        itemView.right.toFloat() + context.dpToPx(40) - deleteIcon.width + dX,
                        itemView.top.toFloat() + (itemView.bottom.toFloat() -
                                itemView.top.toFloat() - deleteIcon.height) / 2,
                        whitePaint
                    )
                }

            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

}