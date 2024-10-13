package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import com.app.tripup.data.model.Itinerary

//import com.app.tripup.data.model.Itinerary

data class ItineraryMainState(
    val data: List<Itinerary> = emptyList(),
    val isLoading: Boolean = false
)
