package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.local.entities.DayItinerary

//Manejamos las variables del state
data class ItinerarySelectionState(
    //Se tiene una lista que tiene las lista de días, junto con la cantidad de activities asociadas
    val dayItinerariesWithCount: List<Pair<DayItinerary, Int>> = emptyList(),
    val itineraryTitle: String = "Itinerary",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
