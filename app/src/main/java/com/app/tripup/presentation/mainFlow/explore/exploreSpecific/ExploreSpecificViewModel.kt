package com.app.tripup.presentation.mainFlow.explore.exploreSpecific

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.repository.FirebasePlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.app.tripup.R
import com.app.tripup.presentation.mainFlow.explore.exploreSpecific.ExploreSpecificState

class ExploreSpecificViewModel(application: Application) : AndroidViewModel(application) {
    //Se crea la instancia del repositorio de Firebase
    private val repository = FirebasePlaceRepository()

    //Se crean variables para manejar y actualizar el state
    private val _uiState = MutableStateFlow(ExploreSpecificState())
    val uiState: StateFlow<ExploreSpecificState> = _uiState.asStateFlow()

    //Se hace una búsqueda de lugares en Firebase
    fun searchPlaces(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                //Se manda a llamar al método de searchPlaces con el repositorio que devuelve una lista de lugares
                val places = repository.searchPlaces(query)
                _uiState.update {
                    if (places.isEmpty()) { //Si viene vacía la lista mostramos mensaje de error
                        // Accede al recurso de cadena desde el contexto de aplicación
                        //Se manda el mensaje de resources base, junto con el query, así se sabe que su búsqueda no fue exitosa
                        val noResultsMessage = getApplication<Application>().getString(
                            R.string.no_results_message,
                            query
                        )
                        //actualizamos el state con el mensaje de error, lista vacío de lugares, y sin cargar
                        it.copy(
                            isLoading = false,
                            places = places,
                            noResultsMessage = noResultsMessage
                        )
                    } else {
                        //quitamos la carga, devolvemos la lista de lugares, y sin mensaje de errror pues hubo algo
                        it.copy(isLoading = false, places = places, noResultsMessage = null)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        places = emptyList(),
                        noResultsMessage = getApplication<Application>().getString(R.string.no_results_message)
                    )
                }
                Log.e("ExploreSpecificViewModel", "Error searching places", e)
            }
        }
    }

}
