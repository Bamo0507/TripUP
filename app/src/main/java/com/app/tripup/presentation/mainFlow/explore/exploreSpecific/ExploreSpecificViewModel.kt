package com.app.tripup.presentation.mainFlow.explore.exploreSpecific

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.FirebasePlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log

class ExploreSpecificViewModel : ViewModel() {

    private val repository = FirebasePlaceRepository()
    private val _uiState = MutableStateFlow(ExploreSpecificState())
    val uiState: StateFlow<ExploreSpecificState> = _uiState.asStateFlow()

    fun searchPlaces(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val places = repository.searchPlaces(query)
                _uiState.update {
                    if (places.isEmpty()) {
                        it.copy(
                            isLoading = false,
                            places = places,
                            noResultsMessage = "No se encontraron resultados para \"$query\""
                        )
                    } else {
                        it.copy(isLoading = false, places = places, noResultsMessage = null)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, places = emptyList(), noResultsMessage = "Error al cargar los datos")
                }
                Log.e("ExploreSpecificViewModel", "Error searching places", e)
            }
        }
    }
}
