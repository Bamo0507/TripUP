// DayActivityViewModel.kt
package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DayActivityViewModel(
    private val activityRepository: ActivityRepository,
    private val dayItineraryId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(DayActivityState())
    val uiState: StateFlow<DayActivityState> = _uiState

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val activities = activityRepository.getActivitiesForDay(dayItineraryId)
                _uiState.value = _uiState.value.copy(activities = activities, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    companion object {
        class Factory(
            private val activityRepository: ActivityRepository,
            private val dayItineraryId: Int
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DayActivityViewModel(activityRepository, dayItineraryId) as T
            }
        }
    }
}
