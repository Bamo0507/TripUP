package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.ActivityRepository
import com.app.tripup.data.repository.DayItineraryRepository
import com.app.tripup.data.repository.ItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItinerarySelectionViewModel(
    private val dayItineraryRepository: DayItineraryRepository,
    private val itineraryRepository: ItineraryRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItinerarySelectionState())
    val uiState: StateFlow<ItinerarySelectionState> = _uiState

    fun loadDaysWithActivityCount(itineraryId: Int) {
        viewModelScope.launch {
            try {
                val days = dayItineraryRepository.getDaysForItinerary(itineraryId)
                val itinerary = itineraryRepository.getItineraryById(itineraryId)

                // Carga el conteo de actividades para cada día
                val daysWithActivityCount = days.map { day ->
                    val activityCount = activityRepository.getActivityCountForDay(day.id)
                    day to activityCount // Retornamos un par (día, conteo de actividades)
                }

                _uiState.value = _uiState.value.copy(
                    dayItinerariesWithCount = daysWithActivityCount,
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
    private val itineraryRepository: ItineraryRepository,
    private val activityRepository: ActivityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItinerarySelectionViewModel(dayItineraryRepository, itineraryRepository, activityRepository) as T
    }
}
