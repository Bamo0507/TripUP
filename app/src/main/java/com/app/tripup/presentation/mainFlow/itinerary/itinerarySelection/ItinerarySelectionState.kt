package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.local.entities.DayItinerary

data class ItinerarySelectionState(
    val dayItinerariesWithCount: List<Pair<DayItinerary, Int>> = emptyList(),
    val itineraryTitle: String = "Itinerary",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
