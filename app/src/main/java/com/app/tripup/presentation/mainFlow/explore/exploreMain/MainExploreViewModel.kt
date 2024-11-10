package com.app.tripup.presentation.mainFlow.explore.exploreMain

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.data.model.Place
import com.app.tripup.data.repository.FirebasePlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class MainExploreViewModel : ViewModel() {
    //Se instancia la FIREBASEPLACEREPOSITORY para ir llamando y pegando los lugares
    private val repository = FirebasePlaceRepository()

    //Variables para manejar el state, uno para el UI el otro escucha constantemente los cambios
    private val _uiState = MutableStateFlow(MainExploreState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    //Función para cargar los lugares por país, se le pasa el país como parámetro
    fun loadPlacesForCountry(countryName: String) {
        viewModelScope.launch {
            try {
                //Se genera una lista de lugares en base a lo que devuelva el repositorio con ayuda de un query
                val places = repository.getPlacesByCountry(countryName)
                //Se actualiza el estado con la lista de lugares y se quita la pantalla de carga
                _uiState.update {
                    it.copy(isLoading = false, data = places)
                }
            } catch (e: Exception) {
                //En caso de error se quita la pantalla de carga y se deja la lista vacía (no hay lugares)
                _uiState.update {
                    it.copy(isLoading = false, data = emptyList())
                }
                Log.e("MainExploreViewModel", "Error loading places", e)
            }
        }
    }

    //Función para obtener el país actual de la persona
    suspend fun getCurrentCountry(context: Context): String? {
        //withContext al parecer se coloca para que se ejecute en un hilo secundario
        return withContext(Dispatchers.IO) {
            try {
                // Se crea una instancia de FusedLocationProviderClient - se usa para obtener la ubicacióh
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                // Se obtiene la última ubicación conocida
                val location = fusedLocationClient.lastLocation.await()

                // Si la ubicación es nula, se intenta obtener la ubicación actual
                val finalLocation = location ?: run {
                    // Solicitar una única actualización de ubicación
                    val cancellationTokenSource = com.google.android.gms.tasks.CancellationTokenSource()
                    val currentLocation = fusedLocationClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        cancellationTokenSource.token
                    ).await()
                    // Limpiar el CancellationTokenSource después de usarlo
                    cancellationTokenSource.cancel()
                    currentLocation
                }

                //Si sí se obtiene una ubicación se hace esto
                if (finalLocation != null) {
                    // Verificar si Geocoder está presente en el dispositivo
                    // Se usa para convertir coordenadas geográficas en direcciones de longitud y latitud
                    if (Geocoder.isPresent()) {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        //Se saca una lista de lugares en base a la latitud y longitud, de la que solo sacamos 1
                        //El 1 es el top result y se espera que sea el correcto
                        val addresses = geocoder.getFromLocation(finalLocation.latitude, finalLocation.longitude, 1)
                        if (addresses?.isNotEmpty() == true) {
                            //Si se encontró algo se devuelve el país
                            addresses[0].countryName
                        } else null
                    } else {
                        Log.e("GeocoderError", "Geocoder not available on this device")
                        null
                    }
                } else {
                    Log.e("LocationError", "Location is null")
                    null
                }
            } catch (e: SecurityException) {
                Log.e("LocationError", "Location permission not granted", e)
                null
            } catch (e: IOException) {
                Log.e("GeocoderError", "Geocoding failed", e)
                null
            } catch (e: Exception) {
                Log.e("LocationError", "Error getting location", e)
                null
            }
        }
    }

    fun onSearchQuerySubmitted(query: String, onSearchComplete: (String) -> Unit) {
        onSearchComplete(query)
    }
}
