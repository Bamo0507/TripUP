package com.app.tripup.data.source

import com.app.tripup.data.model.Itinerary
import java.time.LocalDate

/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.tripup.data.model.Activity
import com.app.tripup.data.model.DayItinerary
import com.app.tripup.data.model.Itinerary

@Database(entities = [Itinerary::class, DayItinerary::class, Activity::class], version = 1)
abstract class ItineraryDb : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao

    companion object {
        @Volatile private var INSTANCE: ItineraryDb? = null

        fun getDatabase(context: Context): ItineraryDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItineraryDb::class.java,
                    "itinerary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
*/

class ItineraryDb {
    // Método simulado para devolver itinerarios. En el futuro, aquí se conectará Room.
    fun getAllItineraries(): List<Itinerary> {
        return listOf(
            Itinerary(1, "Family Trip", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 5), null),
            Itinerary(2, "Business Trip", LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 3), null)
        )
    }
}
