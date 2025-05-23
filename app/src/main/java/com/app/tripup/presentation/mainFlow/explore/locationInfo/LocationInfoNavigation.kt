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
    val countryName: String
)

fun NavController.navigateToLocationInfoScreen(
    destination: LocationInfoDestination,
    navOptions: NavOptions? = null
) {
    this.navigate(
        destination,
        navOptions
    )
}

fun NavGraphBuilder.locationInfoScreen(onBackClick: () -> Unit){
    composable<LocationInfoDestination>{ backStackEntry ->
        //Recibe el id y el countryname, de esta forma se sabe qué nodo buscar, y luego el id para mostrar la información esperada
        val destination: LocationInfoDestination = backStackEntry.toRoute()
        LocationInfoRoute(
            placeId = destination.placeId,
            countryName = destination.countryName,
            onNavigateBack = onBackClick
        )
    }
}
