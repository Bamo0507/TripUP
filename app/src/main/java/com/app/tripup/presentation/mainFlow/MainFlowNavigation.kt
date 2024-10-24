// MainFlowNavigation.kt
package com.app.tripup.presentation.mainFlow

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.mainFlow.account.accountGraph
import com.app.tripup.presentation.mainFlow.explore.exploreGraph

@Serializable
data object MainNavigationGraph

fun NavController.navigateToMainGraph(navOptions: NavOptions? = null) {
    this.navigate(MainNavigationGraph, navOptions)
}

fun NavGraphBuilder.mainNavigationGraph(
    userPreferences: UserPreferences,
    onLogoutClick: () -> Unit
) {
    composable<MainNavigationGraph> {
        val nestedNavController = rememberNavController()
        MainFlowScreen(
            navController = nestedNavController,
            userPreferences = userPreferences,
            onLogoutClick = onLogoutClick
        )
    }
}
