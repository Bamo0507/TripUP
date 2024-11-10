package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

//Todos los datos que maneja el estado
data class ItineraryCreationState(
    val title: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val isFormComplete: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null,
    val itineraryId: Long? = null
)
