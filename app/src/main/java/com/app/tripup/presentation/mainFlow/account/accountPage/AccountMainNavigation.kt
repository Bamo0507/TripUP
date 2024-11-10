package com.app.tripup.presentation.mainFlow.account.accountPage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.tripup.domain.UserPreferences
import kotlinx.serialization.Serializable

//Objeto de identificación
@Serializable
data object AccountDestination

//Método del navgraphbuilder que se llama para mandar navegación y preferences sobre la screen (cómo manejar)
fun NavGraphBuilder.accountScreen(
    onLogoutClick: () -> Unit,
    userPreferences: UserPreferences
) {
    composable<AccountDestination> {
        AccountRoute(
            onLogoutClick = onLogoutClick,
            userPreferences = userPreferences
        )
    }
}
