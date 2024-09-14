package com.app.tripup.data

class PlaceDb {
    private val places: List<Place> = listOf(
        Place(1, "Panama City Beach", "FL", "", "Beautiful beach in Florida", PlaceCategory.HOTELS),
        Place(2, "Rio de Janeiro", "RJ", "", "Famous for its carnival", PlaceCategory.RESTAURANTS),
        Place(3, "Lima", "Peru", "", "Historical city", PlaceCategory.ACTIVITIES),
        Place(4, "New York", "NY", "", "The city that never sleeps", PlaceCategory.DRINKS),
        Place(5, "Barcelona", "Spain", "", "Beautiful architecture", PlaceCategory.HOTELS),
        Place(6, "Paris", "France", "", "City of love", PlaceCategory.RESTAURANTS),
        Place(7, "Tokyo", "Japan", "", "Technology and tradition", PlaceCategory.ACTIVITIES),
        Place(8, "Berlin", "Germany", "", "Historical and modern", PlaceCategory.DRINKS)
    )

    fun getAllPlaces(): List<Place> {
        return places
    }

    fun getPlaceById(id: Int): Place {
        return places.first { it.id == id }
    }

    fun searchPlaces(query: String): List<Place> {
        return getAllPlaces().filter { place ->
            place.name.contains(query, ignoreCase = true) ||
                    place.location.contains(query, ignoreCase = true)
        }
    }

}
