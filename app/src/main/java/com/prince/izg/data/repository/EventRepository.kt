package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.EventApi
import com.prince.izg.data.remote.dto.Event.EventRequest
import com.prince.izg.data.remote.dto.Event.EventResponse
import retrofit2.Response
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventApi: EventApi
) {

    suspend fun addEvent(token: String, event: EventRequest): Response<EventResponse> {
        return eventApi.addEvent("Bearer $token", event)
    }

    suspend fun getEvents(): Response<List<EventResponse>> {
        return eventApi.getEvents()
    }

    suspend fun getEventById(id: Int): Response<EventResponse> {
        return eventApi.getEventById(id)
    }

    suspend fun updateEvent(token: String, id: Int, event: EventRequest): Response<EventResponse> {
        return eventApi.updateEventById("Bearer $token", id, event)
    }

    suspend fun deleteEvent(token: String, id: Int): Response<EventResponse> {
        return eventApi.deleteEventById("Bearer $token", id)
    }
}
