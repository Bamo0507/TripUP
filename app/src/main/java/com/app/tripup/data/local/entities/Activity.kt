package com.app.tripup.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "activities",
    foreignKeys = [
        ForeignKey(
            entity = DayItinerary::class,
            parentColumns = ["id"],
            childColumns = ["dayItineraryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayItineraryId: Int,
    val name: String,
    val startTime: String, // Formato "HH:mm"
    val endTime: String
)
