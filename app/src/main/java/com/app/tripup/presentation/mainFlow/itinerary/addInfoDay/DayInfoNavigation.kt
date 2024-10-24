// DayInfoNavigation.kt
package com.app.tripup.presentation.mainFlow.itinerary.addInfoDay

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/*
@Serializable
data class DayInfoDestination(val dayItineraryId: Int)

fun NavGraphBuilder.dayInfoScreen(
    onActivityCreated: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<DayInfoDestination> { backStackEntry ->
        val dayItineraryId = backStackEntry.arguments?.getInt("dayItineraryId") ?: return@composable
        DayInfoRoute(
            dayItineraryId = dayItineraryId,
            onActivityCreated = onActivityCreated,
            onBackClick = onBackClick
        )
    }
}


 */