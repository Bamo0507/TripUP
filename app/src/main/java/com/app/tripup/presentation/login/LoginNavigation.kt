package com.app.tripup.presentation.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.tripup.domain.UserPreferences
import kotlinx.serialization.Serializable

//Objeto de identificación
@Serializable
data object LoginDestination

//Método con el que se manejara la navegación con el navgraphbuilder
fun NavGraphBuilder.loginScreen(
    //Se pasa lo que se hace cuadno loggeamos y userprefrences para cambiar a logged su estado
    onLoginSuccess: () -> Unit,
    userPreferences: UserPreferences
) {
    composable<LoginDestination> {
        LoginRoute(
            onLoginSuccess = onLoginSuccess,
            userPreferences = userPreferences
        )
    }
}
