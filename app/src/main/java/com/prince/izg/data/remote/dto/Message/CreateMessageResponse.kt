package com.prince.izg.data.remote.dto.Message

data class CreateMessageResponse(
    val id: Int,
    val first_name: String,
    val email: String,
    val phone_number: String,
    val inquiry_type: String,
    val sent_at: String
)
