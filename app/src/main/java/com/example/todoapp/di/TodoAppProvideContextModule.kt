package com.example.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Данила Шабанов on 08.07.2023.
 */
@Module
class TodoAppProvideContextModule(context: Context) {
    private val context: Context

    init {
        this.context = context
    }

    @Singleton
    @Provides
    fun provideContext(): Context = context
}
