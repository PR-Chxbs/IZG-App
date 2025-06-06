package com.prince.izg.data.remote.dto.Event

data class EventRequest(
    val name: String,
    val description: String,
    val event_date: String // Format: YYYY-MM-DD
)
