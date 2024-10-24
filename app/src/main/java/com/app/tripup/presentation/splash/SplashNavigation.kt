// SplashNavigation.kt
package com.app.tripup.presentation.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.tripup.domain.UserPreferences
import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

fun NavGraphBuilder.splashScreen(
    navController: NavController,
    userPreferences: UserPreferences
) {
    composable<SplashDestination> {
        SplashRoute(
            navController = navController,
            userPreferences = userPreferences
        )
    }
}
