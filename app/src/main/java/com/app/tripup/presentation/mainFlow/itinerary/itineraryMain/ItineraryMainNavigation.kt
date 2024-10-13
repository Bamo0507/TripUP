package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ItineraryMainDestination

fun NavGraphBuilder.mainItineraryScreen(
    onItemSelected: (Int) -> Unit,
    onEditClick: () -> Unit,
){
    composable<ItineraryMainDestination> {
        ItineraryMainRoute(
            onItemSelected = onItemSelected,
            onEditClick = onEditClick,
        )
    }
}