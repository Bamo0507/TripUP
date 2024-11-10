package com.app.tripup.presentation.mainFlow.explore.exploreMain

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.tripup.data.model.Place
import kotlinx.serialization.Serializable

//Objeto de identificación
@Serializable
data object MainExploreDestination

fun NavGraphBuilder.mainExploreScreen(
    onPlaceCardClick: (Place) -> Unit,
    onNavigateToSpecific: (String) -> Unit
) {
    composable<MainExploreDestination> {
        MainExploreRoute(
            onPlaceClick = onPlaceCardClick,
            onNavigateToSpecific = onNavigateToSpecific
        )
    }
}
