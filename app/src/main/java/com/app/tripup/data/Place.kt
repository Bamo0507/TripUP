package com.app.tripup.data

enum class PlaceCategory {
    RESTAURANTS, HOTELS, DRINKS, ACTIVITIES
}

data class Place(
    val id: Int,
    val name: String,
    val location: String,
    val imageUrl: String,
    val rating: Double, // Para agregar una calificación o rating
    val description: String,
    val category: PlaceCategory,
    val isFavorite: Boolean = false // Para indicar si el lugar ha sido marcado como favorito
)
