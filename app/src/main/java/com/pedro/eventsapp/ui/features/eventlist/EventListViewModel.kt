package com.pedro.eventsapp.ui.features.eventlist

import androidx.lifecycle.ViewModel
import com.pedro.eventsapp.api.EventsClient
import com.pedro.eventsapp.data.EventItem
import com.pedro.eventsapp.data.network.Resource
import com.pedro.eventsapp.data.network.ResponseHandler

class EventListViewModel(): ViewModel() {
    lateinit var responseHandler: ResponseHandler
    lateinit var eventsClient: EventsClient

    suspend fun fetchEventsList() : Resource<List<EventItem>> {
        return try {
            responseHandler.handleSuccess(eventsClient.getEvents())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}