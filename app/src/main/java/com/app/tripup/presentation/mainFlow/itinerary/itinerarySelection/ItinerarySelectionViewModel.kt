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
    //Inyectamos los 3 repositorios
    private val dayItineraryRepository: DayItineraryRepository,
    private val itineraryRepository: ItineraryRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {

    //Variables para manejar el state
    private val _uiState = MutableStateFlow(ItinerarySelectionState())
    val uiState: StateFlow<ItinerarySelectionState> = _uiState

    //Función para cargar los datos, se pasa el id del itinerario, así sabemos qué buscar
    fun loadDaysWithActivityCount(itineraryId: Int) {
        viewModelScope.launch { //Se lanza la corrutina
            try {
                //Obtenemos los días del itinerario, así como el itinerario asociado al ID
                val days = dayItineraryRepository.getDaysForItinerary(itineraryId)
                val itinerary = itineraryRepository.getItineraryById(itineraryId)

                // Carga el conteo de actividades para cada día
                val daysWithActivityCount = days.map { day ->
                    val activityCount = activityRepository.getActivityCountForDay(day.id)
                    day to activityCount // Retornamos un par (día, conteo de actividades)
                }

                // Actualizamos el state con los días obtenidos y el título del itinerario
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
