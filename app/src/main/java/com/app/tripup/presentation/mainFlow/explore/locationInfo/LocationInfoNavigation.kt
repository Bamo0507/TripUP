package com.app.tripup.presentation.mainFlow.explore.locationInfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class LocationInfoDestination(
    val placeId: Int,
    val isSearchResult: Boolean = false,
)


fun NavController.navigateToLocationInfoScreen(
    destination: LocationInfoDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.locationInfoScreen(onBackClick: () -> Unit){
    composable<LocationInfoDestination>{backStackEntry ->
        val destination: LocationInfoDestination = backStackEntry.toRoute()
        LocationInfoRoute(onNavigateBack = onBackClick,
            placeId = destination.placeId,
            fromSearchDb = destination.isSearchResult
        )

    }
}