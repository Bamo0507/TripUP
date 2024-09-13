package com.app.tripup.data

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val favoritePlaces: List<Place> = emptyList(), // Lugares guardados como favoritos
    val pastItineraries: List<Itinerary> = emptyList() // Historial de itinerarios del usuario
)
