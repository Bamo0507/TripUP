package com.app.tripup.presentation.mainFlow.explore.exploreMain

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.model.Place
import com.app.tripup.data.repository.FirebasePlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log

class MainExploreViewModel : ViewModel() {

    private val repository = FirebasePlaceRepository()

    private val _uiState = MutableStateFlow(MainExploreState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun loadPlacesForCountry(countryName: String) {
        viewModelScope.launch {
            try {
                val places = repository.getPlacesByCountry(countryName)
                _uiState.update {
                    it.copy(isLoading = false, data = places)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, data = emptyList())
                }
                Log.e("MainExploreViewModel", "Error loading places", e)
            }
        }
    }

    fun onSearchQuerySubmitted(query: String, onSearchComplete: (String) -> Unit) {
        onSearchComplete(query)
    }
}
