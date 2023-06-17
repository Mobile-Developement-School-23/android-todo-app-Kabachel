package com.example.todoapp.presentation.todoItems

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodoItemsBinding
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.presentation.todoItems.recyclerview.TodoItemAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt

/**
 * @author Данила Шабанов on 09.06.2023
 */
class TodoItemsFragment : Fragment(R.layout.fragment_todo_items) {

    private val viewModel by viewModels<TodoItemsViewModel>()

    private var _binding: FragmentTodoItemsBinding? = null
    private val binding get() = _binding!!

    private var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoItemsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        binding.todoItemsPlusButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_todoItemsFragment_to_addTodoItemFragment,
                null,
                navOptions {
                    anim {
                        enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
                        exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
                        popEnter = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
                        popExit = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                    }
                }
            )
        }

        binding.todoItemsEyeImageView.setOnClickListener {
            flag = if (flag) {
                binding.todoItemsEyeImageView.setImageResource(R.drawable.ic_eye)
                false
            } else {
                binding.todoItemsEyeImageView.setImageResource(R.drawable.ic_eye_crossed)
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecycler() {
        val itemsAdapter = TodoItemAdapter()
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.todoItemsRecycler.adapter = itemsAdapter
        binding.todoItemsRecycler.layoutManager = layoutManager
        viewModel.handleEvent(Event.Refresh)
        when (viewModel.state) {
            is State.EmptyContent -> itemsAdapter.items = MutableStateFlow(emptyList())
            is State.Content -> itemsAdapter.items =
                MutableStateFlow((viewModel.state as State.Content).screenItems)

            is State.Error -> {}
        }

        binding.todoItemsSubtitle.text = "Выполнено - ${itemsAdapter.items.value.size}"

        setupPullToRefresh(itemsAdapter)
        setupSwipeToRemove(itemsAdapter)
        setupCheckboxClick(itemsAdapter)
    }

    private fun setupPullToRefresh(itemsAdapter: TodoItemAdapter) {
        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                viewModel.handleEvent(Event.Refresh)
                binding.todoItemsSubtitle.text =
                    "Выполнено - ${itemsAdapter.items.value.size}"
                isRefreshing = false
            }
        }
    }

    private fun setupCheckboxClick(itemsAdapter: TodoItemAdapter) {
        itemsAdapter.onTodoItemClick = object : TodoItemAdapter.OnTodoItemClickListener {
            override fun onTodoItemClick(todoItemPreview: TodoItem) {
                todoItemPreview.isDone = !todoItemPreview.isDone
                viewModel.handleEvent(Event.CompleteTask(todoItemPreview))
            }
        }
    }

    private fun setupSwipeToRemove(itemsAdapter: TodoItemAdapter) {

        val trashBinIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_delete, null)

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.handleEvent(Event.DeleteTask(viewHolder.adapterPosition.toString()))
                itemsAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(binding.todoItemsRecycler, "Дело было удалено", Snackbar.LENGTH_LONG)
                    .setAction("Отменить") {
                        Toast.makeText(requireContext(), "Пока такой фичи нет", Toast.LENGTH_SHORT)
                            .show()
                    }.show()
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

                c.clipRect(
                    0f, viewHolder.itemView.top.toFloat(),
                    dX, viewHolder.itemView.bottom.toFloat()
                )

                if (dX < viewHolder.itemView.width / 3) {
                    c.drawColor(Color.GRAY)
                } else {
                    c.drawColor(Color.RED)
                }

                val textMargin =
                    resources.getDimension(androidx.appcompat.R.dimen.abc_action_bar_content_inset_material)
                        .roundToInt()

                if (trashBinIcon != null) {
                    trashBinIcon.bounds = Rect(
                        textMargin,
                        viewHolder.itemView.top + textMargin,
                        textMargin + trashBinIcon.intrinsicWidth,
                        viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                                + textMargin
                    )
                }

                trashBinIcon?.draw(c)

                super.onChildDraw(
                    c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive
                )
            }
        }

        val myHelper = ItemTouchHelper(callback)
        myHelper.attachToRecyclerView(binding.todoItemsRecycler)
    }
}