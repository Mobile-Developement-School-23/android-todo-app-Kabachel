package com.example.todoapp.presentation.addtodoitem

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTodoItemBinding
import com.example.todoapp.domain.entity.PriorityType
import com.example.todoapp.domain.entity.TodoItem
import com.example.todoapp.getAppComponent
import java.time.LocalDateTime
import java.util.Calendar

/**
 * @author Данила Шабанов on 09.06.2023
 */
class AddTodoItemFragment : Fragment(R.layout.fragment_add_todo_item) {

    private val viewModel: AddTodoItemViewModel by viewModels {
        getAppComponent().addTodoItemViewModelFactory()
    }

    private var _binding: FragmentAddTodoItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTodoItemCloseButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addTodoItemSaveText.setOnClickListener {
            val todoItem = TodoItem(
                id = (21..100).random().toString(),
                text = binding.addTodoItemEditText.text.toString(),
                priority = parsePriorityType(binding.addTodoItemPrioritySubtitle.text.toString()),
                deadlineDate = null,
                isDone = false,
                createdDate = LocalDateTime.now(),
                modifiedDate = LocalDateTime.now(),
            )
            viewModel.handleEvent(Event.AddTodoItemPreview(todoItem))
            findNavController().popBackStack()
        }

        binding.addTodoItemPriorityFrame.setOnClickListener {
            showMenu(binding.addTodoItemPriorityFrame)
        }

        binding.addTodoItemDeadlineSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(requireContext(), "SWITCH_ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "SWITCH_OFF", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addTodoItemDeadline.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(), { view, year, monthOfYear, dayOfMonth ->

                },
                year,
                month,
                day
            ).show()
        }
    }

    private fun parsePriorityType(text: String): PriorityType =
        when (text) {
            "Нет" -> PriorityType.LOW
            "Низкий" -> PriorityType.MEDIUM
            else -> {
                PriorityType.HIGH
            }
        }

    private fun showMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.priority_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.popupMenuLowPriority) {
                binding.addTodoItemPrioritySubtitle.text = "Нет"
            }
            if (it.itemId == R.id.popupMenuMediumPriority) {
                binding.addTodoItemPrioritySubtitle.text = "Низкий"
            }
            if (it.itemId == R.id.popupMenuHighPriority) {
                binding.addTodoItemPrioritySubtitle.text = "!! Высокий"
            }

            true
        }

        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
