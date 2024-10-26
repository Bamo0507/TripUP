package com.app.tripup.presentation.mainFlow.itinerary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation

import com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation.ItineraryCreationDestination
import com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation.itineraryCreationScreen
import com.app.tripup.presentation.mainFlow.itinerary.itineraryMain.ItineraryMainDestination
import com.app.tripup.presentation.mainFlow.itinerary.itineraryMain.itineraryMainScreen
import com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection.ItinerarySelectionDestination
import com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection.itinerarySelectionScreen
import com.app.tripup.presentation.mainFlow.itinerary.dayActv.DayActivityDestination
import com.app.tripup.presentation.mainFlow.itinerary.dayActv.dayActivityScreen
import com.app.tripup.presentation.mainFlow.itinerary.addInfoDay.DayInfoDestination
import com.app.tripup.presentation.mainFlow.itinerary.addInfoDay.dayInfoScreen
import com.app.tripup.presentation.mainFlow.itinerary.addInfoDay.navigateToDayInfoScreen
import com.app.tripup.presentation.mainFlow.itinerary.dayActv.navigateToDayActivityScreen

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
        // Pantalla principal
        itineraryMainScreen(
            onItinerarySelected = { itineraryId ->
                navController.navigate(ItinerarySelectionDestination(itineraryId))
            },
            onCreateItinerary = {
                navController.navigate(ItineraryCreationDestination)
            }
        )

        // Pantalla de creación de itinerarios
        itineraryCreationScreen(
            onItineraryCreated = { itineraryId ->
                navController.navigate(ItinerarySelectionDestination(itineraryId.toInt())) {
                    popUpTo(ItineraryMainDestination) {
                        inclusive = true
                    }
                }
            },
            onBackClick = {
                navController.navigateUp()
            }
        )

        // Pantalla tras seleccionar itinerario
        itinerarySelectionScreen(
            onDaySelected = { dayItineraryId, itineraryTitle, date ->
                navController.navigateToDayActivityScreen(
                    DayActivityDestination(
                        dayItineraryId = dayItineraryId,
                        itineraryTitle = itineraryTitle,
                        date = date
                    )
                )
            },
            onBackClick = {
                navController.navigateUp()
            }
        )

        // Pantalla de las activities
        dayActivityScreen(
            onAddActivityClick = { dayItineraryId, itineraryTitle, date ->
                navController.navigateToDayInfoScreen(
                    DayInfoDestination(
                        dayItineraryId = dayItineraryId,
                        itineraryTitle = itineraryTitle,
                        date = date
                    )
                )
            },
            onBackClick = {
                navController.navigateUp()
            }
        )

        // Pantalla de añadir información al día
        dayInfoScreen(
            onActivityCreated = {
                navController.navigateUp()
            },
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}
