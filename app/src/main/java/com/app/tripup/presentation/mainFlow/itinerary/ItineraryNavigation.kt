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
            //Mandamos el ID del itinerario seleccionado
            onItinerarySelected = { itineraryId ->
                navController.navigate(ItinerarySelectionDestination(itineraryId))
            },
            //Si se crea un itinerario dirigimos a esa pantalla
            onCreateItinerary = {
                navController.navigate(ItineraryCreationDestination)
            }
        )

        // Pantalla de creación de itinerarios
        itineraryCreationScreen(
            onItineraryCreated = { itineraryId ->
                //Si se crea un itinerario dirigimos la pantalla de selección de itinerario pasando de una vez el id generado
                navController.navigate(ItinerarySelectionDestination(itineraryId.toInt())) {
                    popUpTo(ItineraryMainDestination) {
                        //Se elimina del backstack la pantalla de creación
                        inclusive = true
                    }
                }
            },
            //Regresamos a la pantalla principal
            onBackClick = {
                navController.navigateUp()
            }
        )

        // Pantalla tras seleccionar itinerario
        itinerarySelectionScreen(
            //Se manda el título para desplegarlo en la pantalla como el ID para desplegar la info asocaida a esa clave
            onDaySelected = { dayItineraryId, itineraryTitle, date ->
                navController.navigateToDayActivityScreen(
                    DayActivityDestination(
                        dayItineraryId = dayItineraryId,
                        itineraryTitle = itineraryTitle,
                        date = date
                    )
                )
            },
            //Regresamos a la pantalla principal
            onBackClick = {
                navController.navigateUp()
            }
        )

        // Pantalla de las activities
        dayActivityScreen(
            //Se manda el título para desplegarlo en la pantalla como el ID para desplegar la info asocaida a esa clave
            onAddActivityClick = { dayItineraryId, itineraryTitle, date ->
                navController.navigateToDayInfoScreen(
                    DayInfoDestination(
                        dayItineraryId = dayItineraryId,
                        itineraryTitle = itineraryTitle,
                        date = date
                    )
                )
            },
            //Regresamos una pantalla atrás
            onBackClick = {
                navController.navigateUp()
            }
        )

        // Pantalla de añadir información al día
        dayInfoScreen(
            //En ambos casos solo debemos regresar una pantalla por si se quiere volver a crear un activity
            onActivityCreated = {
                navController.navigateUp()
            },
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}
