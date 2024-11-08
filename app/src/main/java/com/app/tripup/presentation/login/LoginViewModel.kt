package com.app.tripup.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.domain.UserPreferences
import com.google.firebase.auth.*
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

    fun onLoginEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.LoginClick -> onLogIn()
            LoginEvent.RegisterClick -> onRegister()
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
            if (screenState.email.isEmpty() || screenState.password.isEmpty()) {
                _loginState.update { it.copy(showError = true, errorMessage = "Email and Password are required") }
                return@launch
            }

            _loginState.update { it.copy(isLoading = true) }

            try {
                val loggedStatus = loginRepository.login(screenState.email, screenState.password)
                if (loggedStatus) {
                    val username = extractUsernameFromEmail(screenState.email)
                    userPreferences.setLoggedIn(true)
                    userPreferences.setUserName(screenState.email)

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
                            errorMessage = "Wrong User or Password"
                        )
                    }
                }
            } catch (e: Exception) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = "Error Signing in. Please try again."
                    )
                }
            }
        }
    }

    private fun extractUsernameFromEmail(email: String): String {
        return email.substringBefore("@")
    }



    private fun onRegister() {
        viewModelScope.launch {
            val screenState = _loginState.value
            val emailPattern = Patterns.EMAIL_ADDRESS

            if (screenState.email.isEmpty() || screenState.password.isEmpty()) {
                _loginState.update { it.copy(showError = true, errorMessage = "Email and Password are required") }
                return@launch
            }

            if (!emailPattern.matcher(screenState.email).matches()) {
                _loginState.update { it.copy(showError = true, errorMessage = "Wrong Email Format.") }
                return@launch
            }

            if (screenState.password.length < 6) {
                _loginState.update { it.copy(showError = true, errorMessage = "The Password Must at Least Have 6 Characters.") }
                return@launch
            }

            _loginState.update { it.copy(isLoading = true) }

            try {
                val registerStatus = loginRepository.register(screenState.email, screenState.password)
                if (registerStatus) {
                    val username = extractUsernameFromEmail(screenState.email)
                    userPreferences.setLoggedIn(true)
                    userPreferences.setUserName(screenState.email)

                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
                            loginSuccess = true,
                            email = "",
                            password = "",
                        )
                    }
                }
            }catch (e: FirebaseAuthWeakPasswordException) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = "The password is too weak. It must be at least 6 characters long."
                    )
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = "The email format is invalid."
                    )
                }
            } catch (e: FirebaseAuthUserCollisionException) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = "An account with this email already exists."
                    )
                }
            } catch (e: Exception) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = "Unknown error. Please try again."
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

