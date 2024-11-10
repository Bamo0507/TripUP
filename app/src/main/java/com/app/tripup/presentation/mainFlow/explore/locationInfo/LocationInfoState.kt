package com.app.tripup.presentation.mainFlow.explore.locationInfo

import com.app.tripup.data.model.Place

//Maneja el estado de carga, error si llegar a ocurrir, y el Lugar a mostrar
data class LocationInfoState(
    val isLoading: Boolean = false, // Para indicar si la pantalla está en modo de carga
    val place: Place? = null,       // Información del lugar
    val errorMessage: String = ""
)
