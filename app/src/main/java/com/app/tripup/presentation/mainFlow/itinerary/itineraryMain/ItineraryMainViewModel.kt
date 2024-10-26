package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.ItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItineraryMainViewModel(
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItineraryMainState())
    val uiState: StateFlow<ItineraryMainState> = _uiState

    init {
        loadItineraries()
    }

    private fun loadItineraries() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val itineraries = itineraryRepository.getAllItineraries()
                _uiState.value = _uiState.value.copy(
                    itineraries = itineraries,
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

class ItineraryMainViewModelFactory(
    private val itineraryRepository: ItineraryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItineraryMainViewModel(itineraryRepository) as T
    }
}
