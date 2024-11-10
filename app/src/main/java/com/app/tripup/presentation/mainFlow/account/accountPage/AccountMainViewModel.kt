package com.app.tripup.presentation.mainFlow.account.accountPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AccountMainViewModel(
    // Inyecta la dependencia de UserPreferences
    private val userPreferences: UserPreferences
) : ViewModel() {

    //Creamos los dos states, el que se actualiza constantemente y el que verá la UI
    private val _uiState: StateFlow<AccountMainState>
    val uiState: StateFlow<AccountMainState>
        get() = _uiState

    init {
        _uiState = combine( //Jala 2 flujos de datos, el username y el avatar index de una vez del state
            userPreferences.getUserName(), //Se obtine el username
            userPreferences.getAvatarIndex() //se obtiene el index que deje el usuario
        ) { userName, avatarIndex ->
            // Extrae el nombre de pila del correo si es un correo electrónico
            val displayName = if (userName.contains("@")) {
                //Se le hace un substring para solo jalar lo que antecede al @, y se pone la primera letra en mayúscula
                userName.substringBefore("@").replaceFirstChar { it.uppercase() }
            } else {
                //Si no es un correo electrónico, se da tal cual
                userName
            }
            //Se crea una instancia del state
            AccountMainState(
                userName = displayName,
                currentAvatarIndex = avatarIndex
            )
        }.stateIn( //este método ayuda a actualizar el state
            scope = viewModelScope, //se hará mientras el viewmodel esté vivo
            started = SharingStarted.WhileSubscribed(5000), //Se actualiza si se está usando, se chequea cada 5s
            initialValue = AccountMainState() //actualizamos el state inicial
        )
    }

    //Aumentamos el index del avatar que el usuario eligió
    fun onAvatarClick() {
        viewModelScope.launch {
            //Se hace una función módulo y se le suma 1 para que si se pasa de 6 se reinicie
            //y para que cuando sea 6 no se tenga un 0, pero un 1 por como se tiene en el screen
            val newIndex = (_uiState.value.currentAvatarIndex % 6) + 1
            userPreferences.setAvatarIndex(newIndex)
        }
    }

    //Si nos desloggeamos pasamos el userpreferences a false
    fun onLogoutClick() {
        viewModelScope.launch {
            userPreferences.setLoggedIn(false)
        }
    }

    // Factory para crear el ViewModel, pues al tener que usar userpreferences lo necesitamos para crear el viewmodel
    class Factory(
        private val userPreferences: UserPreferences
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountMainViewModel(userPreferences) as T
        }
    }
}
