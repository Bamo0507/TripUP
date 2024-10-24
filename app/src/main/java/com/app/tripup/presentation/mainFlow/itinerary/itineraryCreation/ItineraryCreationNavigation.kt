// ItineraryCreationNavigation.kt
package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ItineraryCreationDestination

fun NavGraphBuilder.itineraryCreationScreen(
    onItineraryCreated: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable<ItineraryCreationDestination> {
        ItineraryCreationRoute(
            onItineraryCreated = onItineraryCreated,
            onBackClick = onBackClick
        )
    }
}
