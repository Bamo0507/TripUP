// ItineraryCreationViewModel.kt
package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.local.entities.Itinerary
import com.app.tripup.data.repository.ItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItineraryCreationViewModel(
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItineraryCreationState())
    val uiState: StateFlow<ItineraryCreationState> = _uiState

    fun onTitleChanged(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
        checkFormCompletion()
    }

    fun onStartDateChanged(newStartDate: String) {
        _uiState.value = _uiState.value.copy(startDate = newStartDate)
        checkFormCompletion()
    }

    fun onEndDateChanged(newEndDate: String) {
        _uiState.value = _uiState.value.copy(endDate = newEndDate)
        checkFormCompletion()
    }

    private fun checkFormCompletion() {
        val state = _uiState.value
        val isComplete = state.title.isNotEmpty() &&
                state.startDate.isNotEmpty() &&
                state.endDate.isNotEmpty()
        _uiState.value = state.copy(isFormComplete = isComplete)
    }

    fun onSaveItinerary() {
        viewModelScope.launch {
            val state = _uiState.value
            try {
                val itinerary = Itinerary(
                    name = state.title,
                    startDate = state.startDate,
                    endDate = state.endDate
                )
                val itineraryId = itineraryRepository.insertItinerary(itinerary)
                _uiState.value = state.copy(isSaved = true, itineraryId = itineraryId)
            } catch (e: Exception) {
                _uiState.value = state.copy(errorMessage = e.message)
            }
        }
    }
}

class ItineraryCreationViewModelFactory(
    private val itineraryRepository: ItineraryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItineraryCreationViewModel(itineraryRepository) as T
    }
}
