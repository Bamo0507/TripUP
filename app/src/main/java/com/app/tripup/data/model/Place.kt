package com.app.tripup.data.model

enum class PlaceCategory {
    RESTAURANTS, HOTELS, DRINKS, ACTIVITIES
}

data class Place(
    val id: Int = 0,
    val name: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val category: String = "",
    val country: String = "" // Nuevo campo
) {
    val categoryEnum: PlaceCategory
        get() = when (category.uppercase()) {
            "RESTAURANTS" -> PlaceCategory.RESTAURANTS
            "HOTELS" -> PlaceCategory.HOTELS
            "DRINKS" -> PlaceCategory.DRINKS
            "ACTIVITIES" -> PlaceCategory.ACTIVITIES
            else -> PlaceCategory.ACTIVITIES
        }
}
