package com.app.tripup.presentation.login

//Eventos que tendré que manejar
//Edición de contraseña y correo, y click en login
interface LoginEvent {
    data class EmailChanged(val newEmail: String) : LoginEvent
    data class PasswordChanged(val newPassword: String) : LoginEvent
    data object LoginClick : LoginEvent
    data object RegisterClick : LoginEvent // Agrega este evento
}
