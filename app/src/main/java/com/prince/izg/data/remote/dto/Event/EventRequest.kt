package com.prince.izg.data.remote.dto.Event

data class EventRequest(
    val name: String,
    val description: String,
    val event_date: String,  // Format: YYYY-MM-DD
    val location: String,
    val start_time: String,
    val end_time: String,
    val image_url: String?
)
