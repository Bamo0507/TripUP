package com.app.tripup.presentation.splash

//Manejamos las variables del state
data class SplashState(
    //Se tiene una lista que tiene las lista de días, junto con la cantidad de activities asociadas
    val loggedIn: Boolean = false,
    val isLoading: Boolean = false,
)