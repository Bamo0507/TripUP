package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DayActivityViewModel(
    private val activityRepository: ActivityRepository
) : ViewModel() {

    //Variables para manejar el state
    private val _uiState = MutableStateFlow(DayActivityState())
    val uiState: StateFlow<DayActivityState> = _uiState

    //Carga las actividades en la pantalla
    fun loadActivities(dayItineraryId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                //Pasando el ID que es la clave foranea buscamos los activiteies que la tengan y devolvemos una lista de ellas
                val activities = activityRepository.getActivitiesForDay(dayItineraryId)
                //Actualizamos el state, quitando la pantalla de carga, y cargando las actividades
                _uiState.value = _uiState.value.copy(
                    activities = activities,
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

class DayActivityViewModelFactory(
    private val activityRepository: ActivityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DayActivityViewModel(activityRepository) as T
    }
}
