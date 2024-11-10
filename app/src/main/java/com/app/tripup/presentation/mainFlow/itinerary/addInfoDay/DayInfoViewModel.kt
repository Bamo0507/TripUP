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
    //Inyectamos el repositorio de actividades
    private val activityRepository: ActivityRepository
) : ViewModel() {
    //Variables para manejar el STATE
    private val _uiState = MutableStateFlow(DayInfoState())
    val uiState: StateFlow<DayInfoState> = _uiState

    /*
    Funciones para ir actualizando lo que se muestra en los outlinedboxes de la UI
    También, se encarga de verificar si el formulario esta completo en cada uno de ellos
     */
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

    //Se verifica que el state en los 3 parámetros que se llenan no esté vacío, actualiza isFormComplete a true o false acorde a esto
    private fun checkFormCompletion() {
        val state = _uiState.value
        val isComplete = state.activityName.isNotEmpty() &&
                state.startTime.isNotEmpty() &&
                state.endTime.isNotEmpty()
        _uiState.value = state.copy(isFormComplete = isComplete)
    }

    //Al gurdar un activity se crea uno nuevo en la base de datos
    fun onSaveActivity(dayItineraryId: Int) {
        viewModelScope.launch {
            //Se genera una copia del state actual
            val state = _uiState.value
            try {
                //Se crea el activity con los datos del state
                val activity = Activity(
                    dayItineraryId = dayItineraryId,
                    name = state.activityName,
                    startTime = state.startTime,
                    endTime = state.endTime
                )
                //Insertamos el activity en la base de datos local
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
