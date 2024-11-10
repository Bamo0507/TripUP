package com.app.tripup.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//Se genera la tabla en ROOM como day_itineraries
@Serializable
@Entity(tableName = "day_itineraries")

data class DayItinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //Se autogenera el ID para cada día
    val itineraryId: Long, //Clave foránea que ayuda a saber a qué Itinerary pertenece el día
    val date: String //Fecha del día
)
