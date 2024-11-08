package com.app.tripup.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val showError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)
