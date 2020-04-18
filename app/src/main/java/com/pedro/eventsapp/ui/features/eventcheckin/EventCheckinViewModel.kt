package com.pedro.eventsapp.ui.features.eventcheckin

import androidx.lifecycle.ViewModel
import com.pedro.eventsapp.api.EventsClient
import com.pedro.eventsapp.data.CheckinRequest
import com.pedro.eventsapp.data.network.Resource
import com.pedro.eventsapp.data.network.ResponseHandler

class EventCheckinViewModel : ViewModel() {

    lateinit var responseHandler: ResponseHandler
    lateinit var eventsClient: EventsClient

    suspend fun postCheckin(checkinRequest: CheckinRequest): Resource<Void> {
        return try {
            responseHandler.handleSuccess(eventsClient.checkin(checkinRequest))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}