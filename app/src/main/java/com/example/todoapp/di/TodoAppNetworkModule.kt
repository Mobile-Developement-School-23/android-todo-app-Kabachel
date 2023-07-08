package com.example.todoapp.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * @author Данила Шабанов on 08.07.2023.
 */
@Module
internal object TodoAppNetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()
}
