package com.app.tripup.presentation.login

//Variables que se estarán manejando afuera de la UI pero que tienenq ue ver con la UX
data class LoginState(
    val email: String = "",
    val password: String = "",
    val showError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)
