package com.prince.izg.data.remote.dto.Event

data class EventResponse(
    val id: Int,
    val user_id: Int,
    val name: String,
    val description: String,
    val event_date: String,
    val created_at: String,
    val location: String,
    val start_time: String,
    val end_time: String,
    val image_url: String?
)
