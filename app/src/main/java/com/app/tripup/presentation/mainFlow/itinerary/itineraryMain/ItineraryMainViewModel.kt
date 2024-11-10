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
    //Variables para manejar correctamente el state
    private val _uiState = MutableStateFlow(ItineraryMainState())
    val uiState: StateFlow<ItineraryMainState> = _uiState

    //Desde que se genera la instancia cargamos los itinerarios
    init {
        loadItineraries()
    }

    //Función para cargar los itinerarios
    private fun loadItineraries() {
        viewModelScope.launch { //tiramos la corrutina
            _uiState.value = _uiState.value.copy(isLoading = true) //actualizamos el state para que esté en carga
            try {
                //Obtenemos los itinerarios y actualizamos el state
                val itineraries = itineraryRepository.getAllItineraries()
                //Actualizamos el state
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

//Clase para el factory, para poder generar el viewmodel sin problemas con la inyección de dependencias (Repository) al no ser algo base de los viewmodel
class ItineraryMainViewModelFactory(
    private val itineraryRepository: ItineraryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItineraryMainViewModel(itineraryRepository) as T
    }
}
