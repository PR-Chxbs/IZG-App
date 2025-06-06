package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.Event.EventRequest
import com.prince.izg.data.remote.dto.Event.EventResponse
import retrofit2.Response
import retrofit2.http.*

interface EventApi {

    @POST("/events")
    suspend fun addEvent(
        @Header("Authorization") token: String,
        @Body event: EventRequest
    ): Response<EventResponse>

    @GET("/events")
    suspend fun getEvents(): Response<List<EventResponse>>

    @GET("/events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): Response<EventResponse>

    @PUT("/events/{id}")
    suspend fun updateEventById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body event: EventRequest
    ): Response<EventResponse>

    @DELETE("/events/{id}")
    suspend fun deleteEventById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<EventResponse>
}
