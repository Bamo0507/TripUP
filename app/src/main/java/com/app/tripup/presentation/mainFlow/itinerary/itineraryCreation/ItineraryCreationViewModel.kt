// ItineraryCreationViewModel.kt
package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.local.entities.Itinerary
import com.app.tripup.data.local.entities.DayItinerary
import com.app.tripup.data.repository.ItineraryRepository
import com.app.tripup.data.repository.DayItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ItineraryCreationViewModel(
    private val itineraryRepository: ItineraryRepository,
    private val dayItineraryRepository: DayItineraryRepository
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
                generateDaysForItinerary(itineraryId, state.startDate, state.endDate)
                _uiState.value = state.copy(isSaved = true, itineraryId = itineraryId)
            } catch (e: Exception) {
                _uiState.value = state.copy(errorMessage = e.message)
                // Log the error for debugging
                Log.e("ItineraryCreationVM", "Error saving itinerary", e)
            }
        }
    }

    private suspend fun generateDaysForItinerary(itineraryId: Long, startDateStr: String, endDateStr: String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDate = LocalDate.parse(startDateStr, formatter)
        val endDate = LocalDate.parse(endDateStr, formatter)
        var currentDate = startDate

        while (!currentDate.isAfter(endDate)) {
            val dayItinerary = DayItinerary(
                itineraryId = itineraryId,
                date = currentDate.format(formatter)
            )
            dayItineraryRepository.insertDayItinerary(dayItinerary)
            currentDate = currentDate.plusDays(1)
        }
    }
}

class ItineraryCreationViewModelFactory(
    private val itineraryRepository: ItineraryRepository,
    private val dayItineraryRepository: DayItineraryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItineraryCreationViewModel(itineraryRepository, dayItineraryRepository) as T
    }
}
