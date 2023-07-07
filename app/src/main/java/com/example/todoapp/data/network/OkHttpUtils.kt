package com.example.todoapp.data.network

import com.example.todoapp.data.network.entity.BodyResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @author Данила Шабанов on 01.07.2023.
 */
internal fun OkHttpClient.get(url: HttpUrl, authPassword: String): Result<BodyResponse> {
    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "Bearer $authPassword")
        .build()

    this.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    throw IOException(
                        "Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}"
                    )
                }

                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory()).build()
                val jsonAdapterResponse = moshi.adapter(BodyResponse::class.java)
                return@use Result.success(jsonAdapterResponse.fromJson(response.body!!.string()))
            }
        }
    })

    return Result.failure(IOException())
}

fun OkHttpClient.post() {
    TODO()
}