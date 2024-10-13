package com.app.tripup.presentation.mainFlow.itinerary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation.ItineraryCreationDestination
import com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation.itineraryCreation
import com.app.tripup.presentation.mainFlow.itinerary.itineraryMain.ItineraryMainDestination
import com.app.tripup.presentation.mainFlow.itinerary.itineraryMain.mainItineraryScreen
import kotlinx.serialization.Serializable

@Serializable
data object ItineraryNavGraph

fun NavGraphBuilder.itineraryGraph(
    navController: NavController,
){
    //NESTED NAVIGATION FOR ITINERARY SECTION
    navigation<ItineraryNavGraph>(startDestination = ItineraryMainDestination){
        //NAVEGACIÓN DE LA PÁGINA PRINCIPAL
        mainItineraryScreen(
            //PARA CUANDO YA SE TENGA LA LÓGICA DE ESTAS COSAS
            onItemSelected = {

            },
            onEditClick = {
                navController.navigate(ItineraryCreationDestination)
            }
        )

        itineraryCreation(
            onBackClick = {
                navController.navigateUp()
            },
            onCompleteClick = {
                navController.navigateUp()
            }
        )







    }
}