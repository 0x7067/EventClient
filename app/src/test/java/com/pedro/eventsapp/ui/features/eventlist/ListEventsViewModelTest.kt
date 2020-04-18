package com.pedro.eventsapp.ui.features.eventlist

import kotlinx.coroutines.runBlocking
import com.nhaarman.mockitokotlin2.*
import com.pedro.eventsapp.api.EventsClient
import com.pedro.eventsapp.data.EventItem
import com.pedro.eventsapp.data.network.Resource
import com.pedro.eventsapp.data.network.ResponseHandler
import org.junit.After
import org.junit.Before
import org.junit.Test

class ListEventsViewModelTest {

    private val eventListResponse = mock<List<EventItem>>()

    private val eventListResponseResourceSuccess = mock<Resource<List<EventItem>>>()

    private val eventListResponseResourceError = mock<Resource<List<EventItem>>>()

    private val eventClientMock = mock<EventsClient> {
        onBlocking { getEvents() } doReturn eventListResponse
    }

    private val responseHandlerMock = mock<ResponseHandler> {
        on { handleSuccess(eventListResponse) } doReturn eventListResponseResourceSuccess
        on { handleException<List<EventItem>>(any()) } doReturn eventListResponseResourceError
    }

    private val eventListViewModel = ListEventsViewModel()

    @Before
    fun setup() {
        eventListViewModel.responseHandler = responseHandlerMock
        eventListViewModel.eventsClient = eventClientMock
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(eventClientMock, responseHandlerMock)
    }

    @Test
    fun `when fetching event list with success`() =

        runBlocking {

            // when
            eventListViewModel.fetchEventsList()

            // should
            verify(eventClientMock).getEvents()
            verify(responseHandlerMock).handleSuccess(eventListResponse)

            Unit
        }

    @Test
    fun `when fetching event list with error`() =

        runBlocking {

            // given
            val exception = Exception("")

            eventClientMock.stub {
                onBlocking {
                    getEvents() } doThrow exception
            }

            // when
            eventListViewModel.fetchEventsList()

            // should
            verify(eventClientMock).getEvents()
            verify(responseHandlerMock).handleException<List<EventItem>>(exception)

            Unit
        }

}