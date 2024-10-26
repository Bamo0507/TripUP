package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DayActivityDestination(
    val dayItineraryId: Int,
    val itineraryTitle: String,
    val date: String
)

fun NavController.navigateToDayActivityScreen(
    destination: DayActivityDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.dayActivityScreen(
    onAddActivityClick: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    composable<DayActivityDestination> { backStackEntry ->
        val destination: DayActivityDestination = backStackEntry.toRoute()
        DayActivityRoute(
            dayItineraryId = destination.dayItineraryId,
            itineraryTitle = destination.itineraryTitle,
            date = destination.date,
            onAddActivityClick = onAddActivityClick,
            onBackClick = onBackClick
        )
    }
}
