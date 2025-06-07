package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.EventApi
import com.prince.izg.data.remote.dto.Event.EventRequest
import com.prince.izg.data.remote.dto.Event.EventResponse
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventApi: EventApi
) {

    suspend fun addEvent(token: String, event: EventRequest): EventResponse {
        return eventApi.addEvent("Bearer $token", event)
    }

    suspend fun getEvents(): List<EventResponse> {
        return eventApi.getEvents()
    }

    suspend fun getEventById(id: Int): EventResponse {
        return eventApi.getEvent(id)
    }

    suspend fun updateEvent(token: String, id: Int, event: EventRequest): EventResponse {
        return eventApi.updateEventById("Bearer $token", id, event)
    }

    suspend fun deleteEvent(token: String, id: Int): EventResponse {
        return eventApi.deleteEventById("Bearer $token", id)
    }
}
