// AccountNavigation.kt
package com.app.tripup.presentation.mainFlow.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.mainFlow.account.accountPage.AccountDestination
import com.app.tripup.presentation.mainFlow.account.accountPage.accountScreen
import kotlinx.serialization.Serializable

@Serializable
data object AccountNavGraph

fun NavGraphBuilder.accountGraph(
    navController: NavController,
    userPreferences: UserPreferences,
    onLogoutClick: () -> Unit
) {
    navigation<AccountNavGraph>(
        startDestination = AccountDestination
    ) {
        accountScreen(
            onLogoutClick = onLogoutClick,
            userPreferences = userPreferences
        )
    }
}
