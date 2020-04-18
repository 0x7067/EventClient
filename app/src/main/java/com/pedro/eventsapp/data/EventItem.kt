package com.pedro.eventsapp.data

data class EventItem(
    val cupons: List<Cupon>,
    val date: Long,
    val description: String,
    val id: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val people: List<People>,
    val price: Double,
    val title: String
)