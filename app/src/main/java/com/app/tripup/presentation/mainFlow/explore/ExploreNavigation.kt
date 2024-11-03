package com.app.tripup.presentation.mainFlow.explore

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.app.tripup.presentation.mainFlow.explore.exploreMain.MainExploreDestination
import com.app.tripup.presentation.mainFlow.explore.exploreMain.mainExploreScreen
import com.app.tripup.presentation.mainFlow.explore.exploreSpecific.EspecficExploreDestination
import com.app.tripup.presentation.mainFlow.explore.exploreSpecific.navigateToSpecificScreen
import com.app.tripup.presentation.mainFlow.explore.exploreSpecific.specificScreen
import com.app.tripup.presentation.mainFlow.explore.locationInfo.LocationInfoDestination
import com.app.tripup.presentation.mainFlow.explore.locationInfo.locationInfoScreen
import com.app.tripup.presentation.mainFlow.explore.locationInfo.navigateToLocationInfoScreen
import kotlinx.serialization.Serializable

@Serializable
data object ExploreNavGraph

fun NavGraphBuilder.exploreGraph(
    navController: NavController
){
    //NESTED NAVIGATION FOR EXPLORE SECTION
    navigation<ExploreNavGraph>(startDestination = MainExploreDestination){

        //Pantalla principal
        mainExploreScreen(
            onPlaceCardClick = { place ->
                navController.navigateToLocationInfoScreen(
                    destination = LocationInfoDestination(
                        placeId = place.id,
                        countryName = place.country
                    )
                )
            },
            onNavigateToSpecific = { query ->
                navController.navigateToSpecificScreen(
                    destination = EspecficExploreDestination(searchName = query)
                )
            }
        )

        locationInfoScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        specificScreen(
            onPlaceClick = { place ->
                navController.navigateToLocationInfoScreen(
                    destination = LocationInfoDestination(
                        placeId = place.id,
                        countryName = place.country
                    )
                )
            },
            onBackClick = {
                navController.navigateUp()
            }
        )



    }
}
