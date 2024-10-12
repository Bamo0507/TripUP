package com.app.tripup.data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val favoritePlaces: List<Place> = emptyList(), // Lugares guardados como favoritos
    val itineraries: List<Itinerary> = emptyList() // Itinerarios guardados por el usuario
)
