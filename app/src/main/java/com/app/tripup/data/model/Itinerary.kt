package com.app.tripup.data.model

import java.time.LocalDate

/*
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "itineraries")
data class Itinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val notes: String? = null
)
*/

data class Itinerary(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val notes: String? = null,
)