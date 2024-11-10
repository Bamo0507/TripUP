package com.app.tripup.presentation.mainFlow.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.mainFlow.account.accountPage.AccountDestination
import com.app.tripup.presentation.mainFlow.account.accountPage.accountScreen
import kotlinx.serialization.Serializable

//Objeto de identificación
@Serializable
data object AccountNavGraph

//Método del navgraphbuilder que se llama para mandar navegación y preferences sobre la screen (cómo manejar)
fun NavGraphBuilder.accountGraph(
    navController: NavController,
    userPreferences: UserPreferences,
    onLogoutClick: () -> Unit
) {
    //Se maneja como si fuera navegación anidada, porque originalmente se iba a manejar un flujo más complejo
    navigation<AccountNavGraph>(
        startDestination = AccountDestination
    ) {
        accountScreen(
            onLogoutClick = onLogoutClick,
            userPreferences = userPreferences
        )
    }
}
