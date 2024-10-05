package com.app.tripup.data.source

import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory

class SearchPlaceDb {
    private val places: List<Place> = listOf(
        Place(1, "Hotel Guatemala City", "Guatemala", "", "Hotel en Guatemala", PlaceCategory.HOTELS),
        Place(2, "Restaurante Antigua", "Guatemala", "", "Comida típica en Antigua", PlaceCategory.RESTAURANTS),
        Place(3, "Parque Nacional Tikal", "Guatemala", "", "Visita arqueológica", PlaceCategory.ACTIVITIES),
        Place(4, "Bar Zona Viva", "Guatemala", "", "Bar en la Zona Viva", PlaceCategory.DRINKS)
    )

    fun getPlacesBySearch(query: String): List<Place> {
        return places.filter {
            it.location.contains(query, ignoreCase = true) || it.name.contains(query, ignoreCase = true)
        }
    }

    fun getPlaceById(id: Int): Place {
        return places.first { it.id == id }
    }

}
