package com.app.tripup.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
){
    composable<LoginDestination>{
        LoginRoute(
            onLoginSuccess = onLoginSuccess
        )
    }
}