// ItinerarySelectionState.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import com.app.tripup.data.local.entities.DayItinerary

data class ItinerarySelectionState(
    val dayItineraries: List<DayItinerary> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
