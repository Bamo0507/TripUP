package com.app.tripup.presentation.mainFlow.explore.exploreSpecific

import com.app.tripup.data.model.Place

//Se maneja la pantalla de carga, la lista de lugares y el mensaje de no resultados
data class ExploreSpecificState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val noResultsMessage: String? = null
)
