package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import com.app.tripup.data.local.entities.Itinerary

//Manejamos la lista de itinerarios, estado de carga, y mensaje de error
data class ItineraryMainState(
    val itineraries: List<Itinerary> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
