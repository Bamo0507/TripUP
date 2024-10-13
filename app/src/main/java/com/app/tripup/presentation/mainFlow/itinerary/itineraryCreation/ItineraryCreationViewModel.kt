package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class CreateItineraryViewModel : ViewModel() {
    // Variables de estado
    var title = mutableStateOf("")
    var startDate = mutableStateOf<LocalDate?>(null)
    var endDate = mutableStateOf<LocalDate?>(null)

    // Funciones para actualizar el estado
    fun onTitleChanged(newTitle: String) {
        title.value = newTitle
    }

    fun onDatesSelected(start: LocalDate, end: LocalDate) {
        startDate.value = start
        endDate.value = end
    }
}
