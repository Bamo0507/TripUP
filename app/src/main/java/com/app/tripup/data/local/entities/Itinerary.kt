package com.app.tripup.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//Se declara la nueva tabla Itineraries
@Serializable
@Entity(tableName = "itineraries")


data class Itinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //ID autogenerado de identificación para cada itinerario
    val name: String, //nombre
    val startDate: String, // Fecha de inicio
    val endDate: String //Fecha final
)
