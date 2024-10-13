package com.app.tripup.data.source
/*
import androidx.room.*
import com.app.tripup.data.model.Activity
import com.app.tripup.data.model.DayItinerary
import com.app.tripup.data.model.Itinerary

@Dao
interface ItineraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItinerary(itinerary: Itinerary): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayItinerary(dayItinerary: DayItinerary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Query("SELECT * FROM itineraries")
    fun getAllItineraries(): List<Itinerary>

    @Query("SELECT * FROM day_itineraries WHERE itineraryId = :itineraryId")
    fun getDaysForItinerary(itineraryId: Int): List<DayItinerary>

    @Query("SELECT * FROM activities WHERE dayItineraryId = :dayItineraryId")
    fun getActivitiesForDay(dayItineraryId: Int): List<Activity>
}
*/