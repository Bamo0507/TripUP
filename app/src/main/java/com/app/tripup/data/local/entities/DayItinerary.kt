package com.app.tripup.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_itineraries")
data class DayItinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itineraryId: Long,
    val date: String
)
