package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ItineraryCreationDestination

fun NavGraphBuilder.itineraryCreation(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit
){
    composable<ItineraryCreationDestination>{
        ItineraryCreationRoute(onBackClick = onBackClick,
            onCompleteClick = onCompleteClick)
    }

}