package com.example.todoapp.presentation.todoitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodoItemsBinding
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.getAppComponent
import com.example.todoapp.presentation.TodoAppUi
import com.example.todoapp.presentation.resources.TodoAppTheme
import com.example.todoapp.presentation.todoitems.recyclerview.SwipeItemCallback
import com.example.todoapp.presentation.todoitems.recyclerview.TodoItemAdapter
import com.example.todoapp.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Данила Шабанов on 09.06.2023
 */
internal class TodoItemsFragment : Fragment(R.layout.fragment_todo_items) {

    private val viewModel: TodoItemsViewModel by viewModels {
        getAppComponent().todoItemsViewModelsFactory()
    }

    private var _binding: FragmentTodoItemsBinding? = null
    private val binding get() = _binding!!

    private var flag = false

    @Composable
    fun TodoAppScreen() {
        TodoAppUi(
            state = viewModel.state.collectAsState(State.Content(emptyList())).value,
            onEvent = { viewModel.handleEvent(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoItemsBinding.inflate(layoutInflater)
//        return binding.root
        return ComposeView(requireContext()).apply {
            setContent {
                TodoAppTheme {
                    TodoAppScreen()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        binding.todoItemsPlusButton.setOnClickListener {
            handleNavigation(Navigation.AddTodoItem)
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

    private fun handleNavigation(navigation: Navigation) {
        when (navigation) {
            is Navigation.AddTodoItem -> {
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecycler() {
        val counterDoneTodoItems = binding.todoItemsSubtitle
        val recyclerView = binding.todoItemsRecycler

        val itemsAdapter = TodoItemAdapter(viewModel.onEvent)
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = itemsAdapter
        recyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    is State.EmptyContent -> {
                        itemsAdapter.submitList(emptyList())
                        counterDoneTodoItems.text = "0"
                    }

                    is State.Content -> {
                        itemsAdapter.submitList(it.screenItems)
                        counterDoneTodoItems.text =
                            it.screenItems.size.toString()
                    }
                }
            }
        }

        setupPullToRefresh()
        setupCheckboxClick(itemsAdapter)
        setupSwipeToRemove(itemsAdapter, recyclerView)
    }

    private fun setupPullToRefresh() {
        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                viewModel.handleEvent(Event.Refresh)
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

    private fun setupSwipeToRemove(itemsAdapter: TodoItemAdapter, recyclerView: RecyclerView) {
        val callback = SwipeItemCallback(
            context = requireContext(),
            onSwipeLeft = {
                viewModel.onEvent(Event.DeleteTask(itemsAdapter.getItem(it)))
            },
            onSwipeRight = {
                viewModel.onEvent(Event.CompleteTask(itemsAdapter.getItem(it)))
            },
        )

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
