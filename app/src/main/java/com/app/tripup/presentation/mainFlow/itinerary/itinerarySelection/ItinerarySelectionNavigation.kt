// ItinerarySelectionNavigation.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ItinerarySelectionDestination(
    val itineraryId: Int
)

fun NavController.navigateToItinerarySelectionScreen(
    destination: ItinerarySelectionDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.itinerarySelectionScreen(
    onDaySelected: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    composable<ItinerarySelectionDestination> { backStackEntry ->
        val destination: ItinerarySelectionDestination = backStackEntry.toRoute()
        ItinerarySelectionRoute(
            itineraryId = destination.itineraryId,
            onDaySelected = onDaySelected,
            onBackClick = onBackClick
        )
    }
}
