// AppNavigation.kt
package com.app.tripup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.login.LoginDestination
import com.app.tripup.presentation.login.loginScreen
import com.app.tripup.presentation.mainFlow.mainNavigationGraph
import com.app.tripup.presentation.mainFlow.MainNavigationGraph
import com.app.tripup.presentation.splash.SplashDestination
import com.app.tripup.presentation.splash.splashScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    userPreferences: UserPreferences
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination,
        modifier = modifier
    ) {
        splashScreen(navController, userPreferences)

        loginScreen(
            onLoginSuccess = {
                navController.navigate(MainNavigationGraph) {
                    popUpTo(LoginDestination) {
                        inclusive = true
                    }
                }
            },
            userPreferences = userPreferences
        )

        mainNavigationGraph(
            userPreferences = userPreferences,
            onLogoutClick = {
                navController.navigate(LoginDestination) {
                    popUpTo(0)
                }
            }
        )
    }
}
