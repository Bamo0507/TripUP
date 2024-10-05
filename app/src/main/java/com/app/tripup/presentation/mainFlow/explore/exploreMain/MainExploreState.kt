package com.app.tripup.presentation.mainFlow.explore.exploreMain

import com.app.tripup.data.model.Place

data class MainExploreState(
    val isLoading: Boolean = false,
    val data: List<Place> = emptyList()
)