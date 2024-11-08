package com.app.tripup.presentation.mainFlow.account.accountPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AccountMainViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState: StateFlow<AccountMainState>

    val uiState: StateFlow<AccountMainState>
        get() = _uiState

    init {
        _uiState = combine(
            userPreferences.getUserName(),
            userPreferences.getAvatarIndex()
        ) { userName, avatarIndex ->
            // Extrae el nombre de pila del correo si es un correo electrónico
            val displayName = if (userName.contains("@")) {
                userName.substringBefore("@").replaceFirstChar { it.uppercase() }
            } else {
                userName
            }
            AccountMainState(
                userName = displayName,
                currentAvatarIndex = avatarIndex
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AccountMainState()
        )
    }

    fun onAvatarClick() {
        viewModelScope.launch {
            val newIndex = (_uiState.value.currentAvatarIndex % 6) + 1
            userPreferences.setAvatarIndex(newIndex)
            // El uiState se actualizará automáticamente debido al flujo combinado
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            userPreferences.setLoggedIn(false)
        }
    }

    // Factory para crear el ViewModel
    class Factory(
        private val userPreferences: UserPreferences
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountMainViewModel(userPreferences) as T
        }
    }
}
