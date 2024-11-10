package com.app.tripup.presentation.mainFlow.explore.exploreMain

import com.app.tripup.data.model.Place

//Las dos cosas que maneja el state es la lista de lugares y si está cargando algo
data class MainExploreState(
    val isLoading: Boolean = false,
    val data: List<Place> = emptyList(),
    val currentCountry: String? = null
)