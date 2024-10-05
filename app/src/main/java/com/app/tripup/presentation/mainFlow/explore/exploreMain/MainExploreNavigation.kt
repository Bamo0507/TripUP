package com.app.tripup.presentation.mainFlow.explore.exploreMain

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MainExploreDestination

fun NavGraphBuilder.mainExploreScreen(
    onPlaceCardClick: (Int) -> Unit,
    onNavigateToSpecific: (String) -> Unit // Agregar este parámetro para manejar la búsqueda
) {
    composable<MainExploreDestination> {
        MainExploreRoute(
            onPlaceClick = onPlaceCardClick,
            onNavigateToSpecific = onNavigateToSpecific // Pasar la función para redirigir en la búsqueda
        )
    }
}
