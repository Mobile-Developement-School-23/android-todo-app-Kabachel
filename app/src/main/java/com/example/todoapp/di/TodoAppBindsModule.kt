package com.example.todoapp.di

import com.example.todoapp.data.database.mapper.TodoItemDbEntityToDomain
import com.example.todoapp.data.database.mapper.TodoItemDbEntityToDomainImpl
import com.example.todoapp.data.database.mapper.TodoItemDomainToDbEntity
import com.example.todoapp.data.database.mapper.TodoItemDomainToDbEntityImpl
import com.example.todoapp.data.network.NetworkApi
import com.example.todoapp.data.network.NetworkApiImpl
import com.example.todoapp.data.network.mapper.TodoItemNetworkToTodoItemDomain
import com.example.todoapp.data.network.mapper.TodoItemNetworkToTodoItemDomainImpl
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.domain.interactor.TodoItemsInteractor
import com.example.todoapp.domain.interactor.TodoItemsInteractorImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @author Данила Шабанов on 08.07.2023.
 */
@Module
internal interface TodoAppBindsModule {

    @Binds
    @Singleton
    fun bindNetworkApi(
        impl: NetworkApiImpl
    ): NetworkApi

    @Binds
    @Singleton
    fun bindTodoItemsInteractor(
        impl: TodoItemsInteractorImpl
    ): TodoItemsInteractor

    @Binds
    @Singleton
    fun bindTodoItemsRepository(
        impl: TodoItemsRepositoryImpl
    ): TodoItemsRepository

    @Binds
    @Singleton
    fun bindTodoItemDbEntityToDomain(
        impl: TodoItemDbEntityToDomainImpl
    ): TodoItemDbEntityToDomain

    @Binds
    @Singleton
    fun bindTodoItemDomainToDbEntity(
        impl: TodoItemDomainToDbEntityImpl
    ): TodoItemDomainToDbEntity

    @Binds
    @Singleton
    fun bindTodoItemNetworkToTodoItemDomain(
        impl: TodoItemNetworkToTodoItemDomainImpl
    ): TodoItemNetworkToTodoItemDomain
}
