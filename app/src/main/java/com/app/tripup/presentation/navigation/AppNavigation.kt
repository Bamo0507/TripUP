package com.app.tripup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.tripup.presentation.login.LoginDestination
import com.app.tripup.presentation.login.loginScreen
import com.app.tripup.presentation.mainFlow.mainNavigationGraph
import com.app.tripup.presentation.mainFlow.navigateToMainGraph

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        modifier = modifier
    ){
        loginScreen(
            onLoginSuccess = {
                //Aquí colocaré cuando ya tenga el MAIN GRAPH su navegación
                navController.navigateToMainGraph(
                    navOptions = NavOptions.Builder().setPopUpTo<LoginDestination>(
                        inclusive = true
                    ).build()
                )
            }
        )
        //Aquí debo manejar el onlogoutClick más adelante
        mainNavigationGraph(
            onLogoutClick = {
                navController.navigate(LoginDestination){
                    popUpTo(0) //NO ESTOY SEGURO SI YA CON BASE DE DATOS ES IGUAL
                }
            }
        )
    }
}