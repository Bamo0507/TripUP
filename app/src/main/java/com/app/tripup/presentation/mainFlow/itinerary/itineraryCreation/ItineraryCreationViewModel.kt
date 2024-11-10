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
    //Pasamos los dos repositorios que se usarán para modificar el Room
    private val itineraryRepository: ItineraryRepository,
    private val dayItineraryRepository: DayItineraryRepository
) : ViewModel() {
    //Vriables para manejar el estado
    private val _uiState = MutableStateFlow(ItineraryCreationState())
    val uiState: StateFlow<ItineraryCreationState> = _uiState

    //Se va modificando el texto, se guarda y verifica si ya está lleno todo para actualizarse
    fun onTitleChanged(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
        checkFormCompletion()
    }

    //Se modifica el start date y validamos si ya está lleno toddo
    fun onStartDateChanged(newStartDate: String) {
        _uiState.value = _uiState.value.copy(startDate = newStartDate)
        checkFormCompletion()
    }

    //Se modifica el end data y validamos si ya está lleno toddo
    fun onEndDateChanged(newEndDate: String) {
        _uiState.value = _uiState.value.copy(endDate = newEndDate)
        checkFormCompletion()
    }

    //Se verifica que ninguno de los campos de title, start date o end date estén vacíos, si es así se devuelve true
    private fun checkFormCompletion() {
        val state = _uiState.value
        val isComplete = state.title.isNotEmpty() &&
                state.startDate.isNotEmpty() &&
                state.endDate.isNotEmpty()
        _uiState.value = state.copy(isFormComplete = isComplete)
    }

    //Se guarda el itinerario
    fun onSaveItinerary() {
        viewModelScope.launch { //Se lanza la corrutina
            val state = _uiState.value //obtenamos el state actual
            try {
                //Se crea un objeto Itinerary con los datos del state
                val itinerary = Itinerary(
                    name = state.title,
                    startDate = state.startDate,
                    endDate = state.endDate
                )
                //Mandamos a llamar al método de insert que se encarga de guardar la información con ROOM
                val itineraryId = itineraryRepository.insertItinerary(itinerary)
                //Se manda a llamar el método que genera los días del itinerario
                //Se manda el iddel itinerario, la fecha de inicio y la fecha final
                //esto para tener la clave foránea asociada y las fechas para saber cuántos crear con esta clave
                generateDaysForItinerary(itineraryId, state.startDate, state.endDate)
                //Se actualiza el state
                _uiState.value = state.copy(isSaved = true, itineraryId = itineraryId)
            } catch (e: Exception) {
                _uiState.value = state.copy(errorMessage = e.message)
                // Log the error for debugging
                Log.e("ItineraryCreationVM", "Error saving itinerary", e)
            }
        }
    }

    //Se genera los días del itinerario y se guardan en la base de datos local
    private suspend fun generateDaysForItinerary(itineraryId: Long, startDateStr: String, endDateStr: String) {
        //Se declara el patrón de fecha que se necesita
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        //Se formatea el start y end date
        val startDate = LocalDate.parse(startDateStr, formatter)
        val endDate = LocalDate.parse(endDateStr, formatter)

        //guardamos en una variable la fecha actual - se dice actual porque por la lógica va a ir cambiando con cada iteración
        var currentDate = startDate

        //Siempre y cuando la fecha actual no sea mayor a la fecha final creamos días, si no no hacemos nada
        //Esto porque siempre se aumentará el current day por 1, y hasta que estemos en un punto mayor al final
        //Pues ya habremos creado todos los DayItinerary que necesitabamos
        while (!currentDate.isAfter(endDate)) {
            //Creamos un Dayitinerary pasando la clave foránea del itinerario y la fecha
            val dayItinerary = DayItinerary(
                itineraryId = itineraryId,
                date = currentDate.format(formatter)
            )
            //Guardamos en ROOM el DayItinerary que se genero
            dayItineraryRepository.insertDayItinerary(dayItinerary)
            //plusDays es un método que viene de LocalDate, le agrega 1 día a la cuenta
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
