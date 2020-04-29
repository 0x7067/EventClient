package com.pedro.eventsapp.api

import com.pedro.eventsapp.data.CheckinRequest
import com.pedro.eventsapp.data.EventDetail
import com.pedro.eventsapp.data.EventItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface EventsClient {
    @Throws(Exception::class)
    suspend fun getEvents() : List<EventItem>

    @Throws(Exception::class)
    suspend fun getEventDetails(@Path("eventID") eventID: Int): EventDetail

    @Throws(Exception::class)
    suspend fun checkin(@Body checkinRequest: CheckinRequest) : Call<Void>
}

object EventsClientImpl : EventsClient {

    private const val baseUrl = "http://5b840ba5db24a100142dcd8c.mockapi.io"

    private fun getEventsApi(retrofit: Retrofit): EventsApi {
        return retrofit.create(EventsApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient() =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()

    override suspend fun getEvents(): List<EventItem> {
        return getEventsApi(getRetrofit()).getEvents()
    }

    override suspend fun getEventDetails(eventID: Int): EventDetail {
        return getEventsApi(getRetrofit()).getEventDetails(eventID)
    }

    override suspend fun checkin(checkinRequest: CheckinRequest): Call<Void> {
        return getEventsApi(getRetrofit()).checkin(checkinRequest)
    }

}