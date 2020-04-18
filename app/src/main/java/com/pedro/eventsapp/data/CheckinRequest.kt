package com.pedro.eventsapp.data

import com.google.gson.annotations.SerializedName

data class CheckinRequest(
    @SerializedName("eventId") val eventId: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)