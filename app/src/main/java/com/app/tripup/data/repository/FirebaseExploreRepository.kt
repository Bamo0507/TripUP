package com.app.tripup.data.repository

import com.app.tripup.data.model.Place
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.tasks.await
import java.util.Locale

class FirebasePlaceRepository {

    private val database = FirebaseDatabase.getInstance()
    private val placesRef = database.getReference("places")

    suspend fun getAllPlaces(): List<Place> {
        val snapshot = placesRef.get().await()
        val places = mutableListOf<Place>()

        snapshot.children.forEach { countrySnapshot ->
            val countryName = countrySnapshot.key ?: ""
            val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
            placeList?.forEach { place ->
                val placeWithCountry = place.copy(country = countryName)
                places.add(placeWithCountry)
            }
        }
        return places
    }

    suspend fun getPlaceById(id: Int): Place? {
        val snapshot = placesRef.get().await()
        snapshot.children.forEach { countrySnapshot ->
            val countryName = countrySnapshot.key ?: ""
            val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
            placeList?.forEach { place ->
                if (place.id == id) {
                    return place.copy(country = countryName)
                }
            }
        }
        return null
    }

    suspend fun searchPlaces(query: String): List<Place> {
        val snapshot = placesRef.get().await()
        val places = mutableListOf<Place>()

        val cleanedQuery = query.trim().lowercase(Locale.getDefault())

        snapshot.children.forEach { countrySnapshot ->
            val countryName = countrySnapshot.key?.trim() ?: ""
            val cleanedCountryName = countryName.lowercase(Locale.getDefault())
            val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
            placeList?.forEach { place ->
                val placeWithCountry = place.copy(country = countryName)
                val nameMatch = place.name.lowercase(Locale.getDefault()).contains(cleanedQuery)
                val locationMatch = place.location.lowercase(Locale.getDefault()).contains(cleanedQuery)
                val countryMatch = cleanedCountryName.contains(cleanedQuery)
                if (nameMatch || locationMatch || countryMatch) {
                    places.add(placeWithCountry)
                }
            }
        }
        return places
    }


    suspend fun getPlacesByCountry(countryName: String): List<Place> {
        val countrySnapshot = placesRef.child(countryName).get().await()
        val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
        return placeList?.map { it.copy(country = countryName) } ?: emptyList()
    }

    suspend fun getPlaceByIdAndCountry(id: Int, countryName: String): Place? {
        val countrySnapshot = placesRef.child(countryName).get().await()
        val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
        val place = placeList?.firstOrNull { it.id == id }
        return place?.copy(country = countryName)
    }

}
