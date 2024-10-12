package com.app.tripup.data.model

import java.time.LocalDate

data class Itinerary(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val notes: String? = null, // Campo opcional para agregar notas o detalles adicionales
    val days: List<DayItinerary>
)
