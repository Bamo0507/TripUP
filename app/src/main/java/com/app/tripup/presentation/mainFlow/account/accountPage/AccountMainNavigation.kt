package com.app.tripup.presentation.mainFlow.account.accountPage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.tripup.domain.UserPreferences
import kotlinx.serialization.Serializable

@Serializable
data object AccountDestination

// AccountNavigation.kt
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
