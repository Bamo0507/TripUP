package com.app.tripup.data.local.dao

import androidx.room.*
import com.app.tripup.data.local.entities.DayItinerary

@Dao
interface DayItineraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayItinerary(dayItinerary: DayItinerary)

    @Query("SELECT * FROM day_itineraries WHERE itineraryId = :itineraryId")
    suspend fun getDaysForItinerary(itineraryId: Int): List<DayItinerary>

    @Query("SELECT * FROM day_itineraries WHERE id = :id")
    suspend fun getDayItineraryById(id: Int): DayItinerary?

    @Delete
    suspend fun deleteDayItinerary(dayItinerary: DayItinerary)
}