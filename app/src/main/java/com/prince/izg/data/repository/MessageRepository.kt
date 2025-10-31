package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.MessageApi
import com.prince.izg.data.remote.dto.Message.CreateMessageRequest
import com.prince.izg.data.remote.dto.Message.CreateMessageResponse
import com.prince.izg.data.remote.dto.Message.GetMessagesResponse
import retrofit2.Response

class MessageRepository(
    private val messageApi: MessageApi
) {
    suspend fun createMessage(message: CreateMessageRequest): Response<CreateMessageResponse>{
        return messageApi.createMessage(message)
    }

    suspend fun getMessages(): Response<List<GetMessagesResponse>> {
        return messageApi.getMessages()
    }
}