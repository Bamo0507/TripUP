// ItinerarySelectionNavigation.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/*
@Serializable
data class ItinerarySelectionDestination(val itineraryId: Int)

fun NavGraphBuilder.itinerarySelectionScreen(
    onDaySelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable<ItinerarySelectionDestination> { backStackEntry ->
        val itineraryId = backStackEntry.arguments?.getInt("itineraryId") ?: return@composable
        ItinerarySelectionRoute(
            itineraryId = itineraryId,
            onDaySelected = onDaySelected,
            onBackClick = onBackClick
        )
    }
}


 */