// itineraryNavigation.kt
package com.app.tripup.presentation.mainFlow.itinerary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation

import com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation.ItineraryCreationDestination
import com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation.itineraryCreationScreen
import com.app.tripup.presentation.mainFlow.itinerary.itineraryMain.ItineraryMainDestination
import com.app.tripup.presentation.mainFlow.itinerary.itineraryMain.itineraryMainScreen

import kotlinx.serialization.Serializable

@Serializable
data object ItineraryNavGraph

fun NavController.navigateToItineraryGraph(navOptions: NavOptions? = null) {
    this.navigate(ItineraryNavGraph, navOptions)
}

fun NavGraphBuilder.itineraryGraph(
    navController: NavController
){
    navigation<ItineraryNavGraph>(
        startDestination = ItineraryMainDestination
    ){
        //Pantalla principal
        itineraryMainScreen(
            onItinerarySelected = { /*itineraryId ->
                navController.navigate(ItinerarySelectionDestination(itineraryId))
                */
            },
            onCreateItinerary = {
                navController.navigate(ItineraryCreationDestination)
            }
        )

        //Pantalla de creación de itinerarios
        itineraryCreationScreen(
            onItineraryCreated = {/* itineraryId ->
                navController.navigate(ItinerarySelectionDestination(itineraryId.toInt())){
                    popUpTo(ItineraryMainDestination){
                        inclusive = true
                    }
                }
                */
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
        /*
        //Pantalla tras seleccionar itinerario
        itinerarySelectionScreen(
            onDaySelected = { dayItineraryId ->
                navController.navigate(DayActivityDestination(dayItineraryId))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )

        //Pantalla de las activities
        dayActivityScreen(
            onAddActivityClick = {dayItineraryId ->
                navController.navigate(DayInfoDestination(dayItineraryId))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )

        //Pantalla de añadir información al día
        dayInfoScreen(
            onActivityCreated = {
                navController.popBackStack()
            },
            onBackClick = {
                navController.popBackStack()
            }
        )

         */
    }
}