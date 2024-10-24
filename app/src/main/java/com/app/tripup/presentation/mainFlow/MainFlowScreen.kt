package com.app.tripup.presentation.mainFlow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.tripup.presentation.navigation.topLevelDestinations
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.login.LoginViewModel
import com.app.tripup.presentation.mainFlow.account.accountGraph
import com.app.tripup.presentation.mainFlow.account.accountPage.accountScreen
import com.app.tripup.presentation.mainFlow.explore.ExploreNavGraph
import com.app.tripup.presentation.mainFlow.explore.exploreGraph
import com.app.tripup.presentation.mainFlow.itinerary.itineraryGraph
import com.app.tripup.presentation.navigation.BottomNavBar


@Composable
fun MainFlowScreen(
    navController: NavHostController = rememberNavController(),
    userPreferences: UserPreferences,
    onLogoutClick: () -> Unit
){
    var bottomBarVisible by rememberSaveable { mutableStateOf(false) }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    bottomBarVisible = if (currentDestination != null){
        topLevelDestinations.any { destination ->
            currentDestination.hasRoute(destination)
        }
    } else {
        false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = slideInVertically(initialOffsetY = {it}),
                exit = slideOutVertically(targetOffsetY = {it})
            ){
                BottomNavBar(
                    checkItemSelected = { destination ->
                        currentDestination?.hierarchy?.any { it.hasRoute(destination::class) } ?: false
                    },
                    onNavItemClick = { destination ->
                        navController.navigate(destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ExploreNavGraph,
            modifier = Modifier.padding(innerPadding)
        ){
            exploreGraph(navController)

            accountGraph(navController, userPreferences, onLogoutClick)

            itineraryGraph(navController)

        }

    }
}