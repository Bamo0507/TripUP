package com.app.tripup.data.model

import java.time.LocalDate

data class Itinerary(
    val id: Int,
    val name: String,
    val places: List<Place>, // Lugares incluidos en el itinerario
    val startDate: LocalDate,
    val endDate: LocalDate,
    val notes: String? = null // Campo opcional para agregar notas o detalles adicionales
)
