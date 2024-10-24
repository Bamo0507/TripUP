// ItinerarySelectionViewModel.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.DayItineraryRepository
import com.app.tripup.data.repository.ItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ItinerarySelectionViewModel.kt
class ItinerarySelectionViewModel(
    private val dayItineraryRepository: DayItineraryRepository,
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItinerarySelectionState())
    val uiState: StateFlow<ItinerarySelectionState> = _uiState

    fun loadDays(itineraryId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val days = dayItineraryRepository.getDaysForItinerary(itineraryId)
                val itinerary = itineraryRepository.getItineraryById(itineraryId)
                _uiState.value = _uiState.value.copy(
                    dayItineraries = days,
                    itineraryTitle = itinerary?.name ?: "Itinerary",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }
}

class ItinerarySelectionViewModelFactory(
    private val dayItineraryRepository: DayItineraryRepository,
    private val itineraryRepository: ItineraryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItinerarySelectionViewModel(dayItineraryRepository, itineraryRepository) as T
    }
}
