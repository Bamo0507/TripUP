package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

data class ItineraryCreationState(
    val title: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val isFormComplete: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null,
    val itineraryId: Long? = null
)
