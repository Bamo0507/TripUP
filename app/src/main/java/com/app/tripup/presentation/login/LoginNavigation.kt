// LoginNavigation.kt
package com.app.tripup.presentation.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.tripup.domain.UserPreferences
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
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
