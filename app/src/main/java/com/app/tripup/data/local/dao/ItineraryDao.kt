package com.app.tripup.data.local.dao

import androidx.room.*
import com.app.tripup.data.local.entities.Itinerary

@Dao
interface ItineraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItinerary(itinerary: Itinerary): Long

    @Query("SELECT * FROM itineraries")
    suspend fun getAllItineraries(): List<Itinerary>

    @Query("SELECT * FROM itineraries WHERE id = :id")
    suspend fun getItineraryById(id: Int): Itinerary?

    @Delete
    suspend fun deleteItinerary(itinerary: Itinerary)
}