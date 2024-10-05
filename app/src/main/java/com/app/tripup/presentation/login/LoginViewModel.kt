package com.app.tripup.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    // Definir el estado
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    // Eventos que se tienen que ver reflejados en UI
    fun onLoginEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.LoginClick -> onLogIn()
            is LoginEvent.EmailChanged -> onEmailChange(event.newEmail)
            is LoginEvent.PasswordChanged -> onPasswordChange(event.newPassword)
        }
    }

    private fun onEmailChange(email: String) {
        _loginState.update {
            it.copy(
                email = email,
                showError = false
            )
        }
    }

    private fun onPasswordChange(password: String) {
        _loginState.update {
            it.copy(
                password = password,
                showError = false
            )
        }
    } // <- Aquí faltaba el cierre de llave

    private fun onLogIn() {
        // Tener la cortina para el suspend y así tener el delay
        viewModelScope.launch {
            val screenState = _loginState.value
            if (screenState.password.isEmpty()) {
                _loginState.update { it.copy(showError = true) }
                return@launch
            }

            // Primero actualizamos el estado a cargando
            _loginState.update {
                it.copy(isLoading = true)
            }

            // Luego verificamos el login con el repositorio
            val loggedStatus = loginRepository.login(screenState.email, screenState.password)
            if (loggedStatus) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = false,
                        loginSuccess = true,
                        email = "",
                        password = "",
                    )
                }
            } else {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                    )
                }
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer {
                LoginViewModel(
                    loginRepository = LocalLoginRepository()
                )
            }
        }
    }
}
