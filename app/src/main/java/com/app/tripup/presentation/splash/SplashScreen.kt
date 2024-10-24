// SplashScreen.kt
package com.app.tripup.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.login.LoginDestination
import com.app.tripup.presentation.mainFlow.MainNavigationGraph

@Composable
fun SplashRoute(
    navController: NavController,
    userPreferences: UserPreferences,
    modifier: Modifier = Modifier
) {
    val viewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(userPreferences)
    )
    SplashScreen(navController = navController, viewModel = viewModel, modifier = modifier)
}

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    modifier: Modifier = Modifier
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = null)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true) {
            navController.navigate(MainNavigationGraph) {
                popUpTo(SplashDestination) {
                    inclusive = true
                }
            }
        } else if (isLoggedIn == false) {
            navController.navigate(LoginDestination) {
                popUpTo(SplashDestination) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

