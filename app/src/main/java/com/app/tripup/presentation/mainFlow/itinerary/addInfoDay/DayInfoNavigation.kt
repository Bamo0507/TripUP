package com.app.tripup.presentation.mainFlow.itinerary.addInfoDay

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DayInfoDestination(
    val dayItineraryId: Int,
    val itineraryTitle: String,
    val date: String
)

fun NavController.navigateToDayInfoScreen(
    destination: DayInfoDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.dayInfoScreen(
    onActivityCreated: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<DayInfoDestination> { backStackEntry ->
        val destination: DayInfoDestination = backStackEntry.toRoute()
        DayInfoRoute(
            dayItineraryId = destination.dayItineraryId,
            itineraryTitle = destination.itineraryTitle,
            date = destination.date,
            onActivityCreated = onActivityCreated,
            onBackClick = onBackClick
        )
    }
}
