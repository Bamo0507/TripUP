// DayActvNavigation.kt
package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/*
@Serializable
data class DayActivityDestination(val dayItineraryId: Int)

fun NavGraphBuilder.dayActivityScreen(
    onAddActivityClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable<DayActivityDestination> { backStackEntry ->
        val dayItineraryId = backStackEntry.arguments?.getInt("dayItineraryId") ?: return@composable
        DayActivityRoute(
            dayItineraryId = dayItineraryId,
            onAddActivityClick = onAddActivityClick,
            onBackClick = onBackClick
        )
    }
}


 */