package com.example.todoapp

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.todoapp.di.DaggerTodoAppComponent
import com.example.todoapp.di.TodoAppComponent
import com.example.todoapp.di.TodoAppProvideContextModule

/**
 * @author Данила Шабанов on 08.07.2023.
 */
internal class TodoAppApplication : Application() {
    val appComponent: TodoAppComponent = DaggerTodoAppComponent.builder().todoAppProvideContextModule(
        TodoAppProvideContextModule(this)
    ).build()
}

internal fun Fragment.getAppComponent(): TodoAppComponent =
    (requireContext().applicationContext as TodoAppApplication).appComponent
