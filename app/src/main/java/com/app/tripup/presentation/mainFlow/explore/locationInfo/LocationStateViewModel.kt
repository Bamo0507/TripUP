package com.app.tripup.presentation.mainFlow.explore.locationInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.model.Place
import com.app.tripup.data.source.PlaceDb
import com.app.tripup.data.source.SearchPlaceDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationInfoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Bases de datos simuladas
    private val placeDb = PlaceDb()
    private val searchPlaceDb = SearchPlaceDb()

    // Estado mutable observado por la UI
    private val _uiState = MutableStateFlow(LocationInfoState())
    val uiState = _uiState.asStateFlow()

    init {
        val placeId = savedStateHandle.get<Int>("placeId")
        val fromSearchDb = savedStateHandle.get<Boolean>("fromSearchDb") ?: false // Valor por defecto en caso de que no se proporcione

        // Verificamos si el placeId es válido antes de proceder
        if (placeId != null) {
            loadPlaceInfo(placeId, fromSearchDb) // Ahora pasamos ambos parámetros
        } else {
            // Si el placeId es nulo, manejamos el error
            _uiState.value = _uiState.value.copy(
                place = null,
                errorMessage = "Invalid place ID"
            )
        }
    }


    // Función para cargar la información de un lugar
    fun loadPlaceInfo(placeId: Int, fromSearchDb: Boolean) {
        viewModelScope.launch {
            val place = if (fromSearchDb) {
                searchPlaceDb.getPlaceById(placeId)
            } else {
                placeDb.getPlaceById(placeId)
            }
            _uiState.value = _uiState.value.copy(place = place)
        }
    }
}
