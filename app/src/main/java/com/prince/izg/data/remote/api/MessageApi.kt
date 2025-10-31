package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.Message.CreateMessageRequest
import com.prince.izg.data.remote.dto.Message.CreateMessageResponse
import com.prince.izg.data.remote.dto.Message.GetMessagesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApi {

    @POST("messages")
    suspend fun createMessage(
        @Body message: CreateMessageRequest
    ): Response<CreateMessageResponse>

    @GET("messages")
    suspend fun getMessages(): Response<List<GetMessagesResponse>>
}