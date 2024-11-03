package com.app.tripup.presentation.mainFlow.explore.locationInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.FirebasePlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationInfoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository = FirebasePlaceRepository()

    private val _uiState = MutableStateFlow(LocationInfoState())
    val uiState = _uiState.asStateFlow()

    init {
        val placeId = savedStateHandle.get<Int>("placeId")
        val countryName = savedStateHandle.get<String>("countryName")
        if (placeId != null && countryName != null) {
            loadPlaceInfo(placeId, countryName)
        } else {
            _uiState.value = _uiState.value.copy(
                place = null,
                errorMessage = "Datos de lugar inválidos"
            )
        }
    }

    fun loadPlaceInfo(placeId: Int, countryName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val place = repository.getPlaceByIdAndCountry(placeId, countryName)
                _uiState.value = _uiState.value.copy(place = place, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(place = null, errorMessage = "Error al cargar el lugar", isLoading = false)
            }
        }
    }
}
