package com.app.tripup.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    userPreferences: UserPreferences
) : ViewModel() {

    //Variables para manejar el state
    private val _uiState = MutableStateFlow(SplashState())
    val uiState: StateFlow<SplashState> = _uiState

    // Observa el estado de inicio de sesión y actualiza el UI State
    init {
        viewModelScope.launch { //Lnazamos la corrutina
            userPreferences.isLoggedIn()
                .collectLatest { isLoggedIn -> //Vemos si está loggeado
                    //Actualizamos el state
                    _uiState.value = SplashState(
                        isLoading = false,
                        loggedIn = isLoggedIn
                    )
                }
        }
    }
    companion object {
        fun provideFactory(
            userPreferences: UserPreferences
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SplashViewModel(userPreferences) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
