package com.example.todoapp.data.network

import com.example.todoapp.data.network.entity.BodyResponse
import com.example.todoapp.data.network.utils.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * @author Данила Шабанов on 30.06.2023.
 */
internal interface NetworkApi {

    suspend fun getTodoItems(): Result<BodyResponse>
}

internal class NetworkApiImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : NetworkApi {
    private val baseUrl: HttpUrl = BASE_URL.toHttpUrl()
    private val authPassword = "latecomers"

    override suspend fun getTodoItems(): Result<BodyResponse> =
        withContext(Dispatchers.IO) {
            okHttpClient.get(baseUrl, authPassword)
        }

    companion object {
        private const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    }
}
