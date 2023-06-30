package com.example.todoapp.data.network

import com.example.todoapp.data.network.entity.BodyResponse
import com.example.todoapp.domain.entity.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

/**
 * @author Данила Шабанов on 30.06.2023.
 */
internal interface NetworkApi {

    suspend fun getTodoItems(): Result<BodyResponse>
}

internal class NetworkApiImpl(
    baseUrl: String = "https://beta.mrdekk.ru/todobackend/",
    private val okHttpClient: OkHttpClient = OkHttpClient(),
) : NetworkApi {
    private val baseUrl: HttpUrl = baseUrl.toHttpUrl()
    private val authPassword = "latecomers"

    override suspend fun getTodoItems(): Result<BodyResponse> =
        withContext(Dispatchers.IO) {
            okHttpClient.get(baseUrl, authPassword)
        }
}