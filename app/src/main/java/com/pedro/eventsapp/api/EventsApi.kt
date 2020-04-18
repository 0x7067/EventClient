package com.pedro.eventsapp.api

import com.pedro.eventsapp.data.EventDetail
import com.pedro.eventsapp.data.EventItem
import retrofit2.http.GET
import retrofit2.http.Path

interface EventsApi {

    @GET("/api/events")
    suspend fun getEvents() : List<EventItem>

    @GET("api/events/{eventID})")
    suspend fun getEventDetails(@Path("eventID") eventID: Int): EventDetail
}