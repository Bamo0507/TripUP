package com.app.tripup.presentation.mainFlow.explore.exploreMain

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.source.PlaceDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainExploreViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val placeDb = PlaceDb()

    private val _uiState = MutableStateFlow(MainExploreState(isLoading = true)) // Inicializamos isLoading como true
    val uiState = _uiState.asStateFlow()

    init {
        loadPlaces() // Cargamos los lugares al inicializar el ViewModel
    }

    fun loadPlaces() {
        viewModelScope.launch {
            // Simulación de cargar lugares (puedes añadir una pausa aquí si es necesario)
            val places = placeDb.getAllPlaces()

            // Actualizamos el estado con los datos cargados
            _uiState.update {
                it.copy(isLoading = false, data = places)
            }
        }
    }

    fun onSearchQuerySubmitted(query: String, onSearchComplete: (String) -> Unit) {
        // Aquí se puede realizar una búsqueda si tienes un sistema de filtrado en tu base de datos
        onSearchComplete(query) // Redirige al `ExploreSpecificScreen` con el término de búsqueda
    }

}
