package com.app.tripup.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//Entities para Actividades
@Serializable
//@Entity genera una tabla en base a la clase dada
@Entity(
    tableName = "activities",
    foreignKeys = [
        //Se genera una relación de "clave foránea" entre la tabla actvities y la tabla day_itineraries
        ForeignKey(
            entity = DayItinerary::class, //Indicamos que una actividad viene de un Dayitinerary
            parentColumns = ["id"], //Indica que la columna ID de la tabla DayItinerary es la clave primaria que conecta
            childColumns = ["dayItineraryId"], //Es la clave foránea que se encuentra en la tabla de Activities
            onDelete = ForeignKey.CASCADE //Si se elimina un DayItinerary, se eliminarán todas las actividades relacionadas (se evalúa su implementación)
        )
    ]
)
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //Clave primaria autogenerada - ID de la actividad
    val dayItineraryId: Int, //ID del DayItinerary al que pertenece la actividad
    val name: String, //Nombre de la actividad
    val startTime: String, // Hora de inicio
    val endTime: String //Hora de finalización
)
