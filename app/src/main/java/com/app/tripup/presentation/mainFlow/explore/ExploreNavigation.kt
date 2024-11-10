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
            //Si se da click sobre una carta
            onPlaceCardClick = { place ->
                //Navegamos a la info screen
                navController.navigateToLocationInfoScreen(
                    //Nos aseguramos que la class locationinfodestination
                    //reciba el id y el countryname y los guarde de una vez
                    //ambos son necesarios para buscar la info en la database
                    destination = LocationInfoDestination(
                        placeId = place.id,
                        countryName = place.country
                    )
                )
            },
            //Si se realiza una búsqueda, al dar enter en la barra de búsqueda mandamos el query
            onNavigateToSpecific = { query ->
                navController.navigateToSpecificScreen(
                    destination = EspecficExploreDestination(searchName = query)
                )
            }
        )

        //Solamente se va para atrás
        locationInfoScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        specificScreen(
            //Navegamos a la info screen
            onPlaceClick = { place ->
                //Nos aseguramos que la class locationinfodestination
                //reciba el id y el countryname y los guarde de una vez
                //ambos son necesarios para buscar la info en la database
                navController.navigateToLocationInfoScreen(
                    destination = LocationInfoDestination(
                        placeId = place.id,
                        countryName = place.country
                    )
                )
            },
            //Regresamos a la pantalla anterior
            onBackClick = {
                navController.navigateUp()
            }
        )



    }
}
