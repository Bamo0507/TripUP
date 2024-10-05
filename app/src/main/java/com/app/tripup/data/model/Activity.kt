package com.app.tripup.data.model

import java.time.LocalTime

data class Activity(
    val id: Int,
    val name: String,
    val description: String,
    val place: Place, // Lugar donde se lleva a cabo la actividad
    val startTime: LocalTime,
    val endTime: LocalTime
)
