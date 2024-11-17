package com.app.tripup.presentation.mainFlow.explore.locationInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.FirebasePlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationStateViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Se crea la instancia del repositorio de Firebase
    private val repository = FirebasePlaceRepository()

    // VARIABLES PARA EL ESTADO
    private val _uiState = MutableStateFlow(LocationInfoState())
    val uiState = _uiState.asStateFlow()

    //Se ejecuta no más tengamos la instancia de la clase
    init {
        //Recuperamos el id y el country
        val placeId = savedStateHandle.get<Int>("placeId")
        val countryName = savedStateHandle.get<String>("countryName")
        if (placeId != null && countryName != null) {
            //Carga el lugar para que se muestre en pantalla
            loadPlaceInfo(placeId, countryName)
        } else {
            //El state se actualiza
            _uiState.value = _uiState.value.copy(
                place = null,
                errorMessage = "Datos de lugar inválidos"
            )
        }
    }

    //Cargar el lugar y obtenerlo de firebase
    fun loadPlaceInfo(placeId: Int, countryName: String) {
        viewModelScope.launch {
            //Ponemos la pantalla en carga
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                //Se obtiene el lugar, buscando el nodo con el countryname, y luego buscando el ID
                val place = repository.getPlaceByIdAndCountry(placeId, countryName)
                _uiState.value = _uiState.value.copy(place = place, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(place = null, errorMessage = "Error al cargar el lugar", isLoading = false)
            }
        }
    }
}
