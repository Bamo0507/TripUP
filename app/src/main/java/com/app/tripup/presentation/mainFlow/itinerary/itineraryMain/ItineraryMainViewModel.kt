package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.source.ItineraryDb // Conexión con Room o base de datos simulada
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItineraryMainViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itineraryDb = ItineraryDb() // Aquí usaremos Room o una DB simulada
    private val _uiState: MutableStateFlow<ItineraryMainState> = MutableStateFlow(
        ItineraryMainState()
    ) // Estado inicial vacío
    val uiState = _uiState.asStateFlow() // Estado expuesto para la UI

    // Función para obtener la lista de itinerarios desde la base de datos
    fun getListItineraries() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            // Simulación de un retraso antes de obtener los datos
            delay(2000) // O puede no haber delay si es inmediato

            // Obtener itinerarios desde la base de datos
            val itineraries = itineraryDb.getAllItineraries() // Aquí te conectas con Room

            _uiState.update { state ->
                state.copy(
                    data = itineraries,
                    isLoading = false
                )
            }
        }
    }
}
