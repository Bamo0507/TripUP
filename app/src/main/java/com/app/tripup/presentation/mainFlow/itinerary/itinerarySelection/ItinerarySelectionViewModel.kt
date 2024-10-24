// ItinerarySelectionViewModel.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.DayItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItinerarySelectionViewModel(
    private val dayItineraryRepository: DayItineraryRepository,
    private val itineraryId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItinerarySelectionState())
    val uiState: StateFlow<ItinerarySelectionState> = _uiState

    init {
        loadDays()
    }

    private fun loadDays() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val days = dayItineraryRepository.getDaysForItinerary(itineraryId)
                _uiState.value = _uiState.value.copy(dayItineraries = days, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    companion object {
        class Factory(
            private val dayItineraryRepository: DayItineraryRepository,
            private val itineraryId: Int
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ItinerarySelectionViewModel(dayItineraryRepository, itineraryId) as T
            }
        }
    }
}
