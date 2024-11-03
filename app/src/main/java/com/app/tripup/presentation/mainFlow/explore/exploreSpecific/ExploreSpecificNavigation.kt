package com.app.tripup.presentation.mainFlow.explore.exploreSpecific

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.app.tripup.data.model.Place
import com.app.tripup.presentation.mainFlow.explore.locationInfo.LocationInfoDestination
import kotlinx.serialization.Serializable

@Serializable
data class EspecficExploreDestination(
    val searchName: String
)

fun NavController.navigateToSpecificScreen(
    destination: EspecficExploreDestination,
    navOptions: NavOptions? = null
){
    this.navigate(
        destination,
        navOptions
    )
}


fun NavGraphBuilder.specificScreen(
    onBackClick: () -> Unit,
    onPlaceClick: (Place) -> Unit
){
    composable<EspecficExploreDestination>{ backStackEntry ->
        val destination: EspecficExploreDestination = backStackEntry.toRoute()

        ExploreSpecificRoute(
            onBackClick = onBackClick,
            onPlaceClick = onPlaceClick,
            query = destination.searchName
        )
    }
}

