package com.app.tripup.data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val itineraries: List<Itinerary> = emptyList() // Itinerarios guardados por el usuario
)
