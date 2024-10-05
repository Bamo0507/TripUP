package com.app.tripup.data.model

enum class PlaceCategory {
    RESTAURANTS, HOTELS, DRINKS, ACTIVITIES
}

data class Place(
    val id: Int,
    val name: String,
    val location: String,
    val imageUrl: String,
    val description: String,
    val category: PlaceCategory,
    val isFavorite: Boolean = false // Para indicar si el lugar ha sido marcado como favorito
)
