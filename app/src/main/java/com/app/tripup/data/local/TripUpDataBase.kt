package com.app.tripup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.tripup.data.local.dao.ActivityDao
import com.app.tripup.data.local.dao.DayItineraryDao
import com.app.tripup.data.local.dao.ItineraryDao
import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.local.entities.DayItinerary
import com.app.tripup.data.local.entities.Itinerary

//Se define la base de datos de manera completa
@Database(
    entities = [Itinerary::class, DayItinerary::class, Activity::class], //Se declaran los 3 tipos de entities que se van a usar
    version = 1,
    exportSchema = false //No se exporta el esquema de la base de datos
)

//TripUpDataBase - identificador para la base de datos en ROOM o de la clase al menos
abstract class TripUpDatabase : RoomDatabase() {
    //Se definen los DAOs que se van a usar en la base de datos
    abstract fun itineraryDao(): ItineraryDao
    abstract fun dayItineraryDao(): DayItineraryDao
    abstract fun activityDao(): ActivityDao
}
