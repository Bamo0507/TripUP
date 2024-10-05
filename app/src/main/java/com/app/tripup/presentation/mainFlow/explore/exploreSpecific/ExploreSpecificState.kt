package com.app.tripup.presentation.mainFlow.explore.exploreSpecific

import com.app.tripup.data.model.Place
data class ExploreSpecificState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val noResultsMessage: String? = null
)
