// DayInfoViewModel.kt
package com.app.tripup.presentation.mainFlow.itinerary.addInfoDay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DayInfoViewModel(
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DayInfoState())
    val uiState: StateFlow<DayInfoState> = _uiState

    fun onActivityNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(activityName = newName)
        checkFormCompletion()
    }

    fun onStartTimeChanged(newStartTime: String) {
        _uiState.value = _uiState.value.copy(startTime = newStartTime)
        checkFormCompletion()
    }

    fun onEndTimeChanged(newEndTime: String) {
        _uiState.value = _uiState.value.copy(endTime = newEndTime)
        checkFormCompletion()
    }

    private fun checkFormCompletion() {
        val state = _uiState.value
        val isComplete = state.activityName.isNotEmpty() &&
                state.startTime.isNotEmpty() &&
                state.endTime.isNotEmpty()
        _uiState.value = state.copy(isFormComplete = isComplete)
    }

    fun onSaveActivity(dayItineraryId: Int) {
        viewModelScope.launch {
            val state = _uiState.value
            try {
                val activity = Activity(
                    dayItineraryId = dayItineraryId,
                    name = state.activityName,
                    startTime = state.startTime,
                    endTime = state.endTime
                )
                activityRepository.insertActivity(activity)
            } catch (e: Exception) {
                _uiState.value = state.copy(errorMessage = e.message)
            }
        }
    }
}

class DayInfoViewModelFactory(
    private val activityRepository: ActivityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DayInfoViewModel(activityRepository) as T
    }
}
