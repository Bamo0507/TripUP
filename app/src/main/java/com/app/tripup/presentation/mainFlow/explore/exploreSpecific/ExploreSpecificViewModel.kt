package com.app.tripup.presentation.mainFlow.explore.exploreSpecific

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.source.PlaceDb
import com.app.tripup.data.source.SearchPlaceDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreSpecificViewModel : ViewModel() {
    private val searchDb = SearchPlaceDb()
    private val _uiState = MutableStateFlow(ExploreSpecificState())
    val uiState: StateFlow<ExploreSpecificState> = _uiState

    fun searchPlaces(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(1000)  // Simular carga
            val places = searchDb.getPlacesBySearch(query)
            if (places.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    places = places,
                    noResultsMessage = "No se encontraron resultados para $query"
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    places = places,
                    noResultsMessage = null
                )
            }
        }
    }

}
