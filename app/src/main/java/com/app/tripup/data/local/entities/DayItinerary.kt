// DayItinerary.kt
package com.app.tripup.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "day_itineraries",
    foreignKeys = [
        ForeignKey(
            entity = Itinerary::class,
            parentColumns = ["id"],
            childColumns = ["itineraryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DayItinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itineraryId: Int,
    val date: String // Fecha del día específico
)
