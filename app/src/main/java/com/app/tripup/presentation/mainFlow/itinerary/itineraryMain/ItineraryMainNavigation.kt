package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ItineraryMainDestination

fun NavGraphBuilder.itineraryMainScreen(
    onItinerarySelected: (Int) -> Unit,
    onCreateItinerary: () -> Unit
) {
    composable<ItineraryMainDestination> {
        ItineraryMainRoute(
            onItinerarySelected = onItinerarySelected,
            onCreateItinerary = onCreateItinerary
        )
    }
}
