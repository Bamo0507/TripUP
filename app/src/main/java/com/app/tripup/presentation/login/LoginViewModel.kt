package com.app.tripup.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

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
    }

    private fun onLogIn() {
        viewModelScope.launch {
            val screenState = _loginState.value
            if (screenState.password.isEmpty()) {
                _loginState.update { it.copy(showError = true) }
                return@launch
            }

            _loginState.update { it.copy(isLoading = true) }

            delay(4000)

            val loggedStatus = loginRepository.login(screenState.email, screenState.password)
            if (loggedStatus) {
                // Actualizamos UserPreferences
                userPreferences.setLoggedIn(true)
                userPreferences.setUserName(screenState.email) // o el nombre de usuario correspondiente

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

    fun onLogout() {
        viewModelScope.launch {
            userPreferences.setLoggedIn(false)
            _loginState.update {
                it.copy(
                    loginSuccess = false
                )
            }
        }
    }

    companion object {
        // Actualizamos Factory
        class Factory(
            private val loginRepository: LoginRepository,
            private val userPreferences: UserPreferences
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(loginRepository, userPreferences) as T
            }
        }
    }

}
