// TripUpDatabase.kt
package com.app.tripup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.tripup.data.local.dao.ActivityDao
import com.app.tripup.data.local.dao.DayItineraryDao
import com.app.tripup.data.local.dao.ItineraryDao
import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.local.entities.DayItinerary
import com.app.tripup.data.local.entities.Itinerary

@Database(
    entities = [Itinerary::class, DayItinerary::class, Activity::class],
    version = 1,
    exportSchema = false
)
abstract class TripUpDatabase : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao
    abstract fun dayItineraryDao(): DayItineraryDao
    abstract fun activityDao(): ActivityDao
}
