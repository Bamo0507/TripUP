package com.app.tripup.presentation.mainFlow.account

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.app.tripup.presentation.login.LoginDestination
import com.app.tripup.presentation.login.LoginViewModel
import com.app.tripup.presentation.mainFlow.account.accountPage.AccountDestination
import com.app.tripup.presentation.mainFlow.account.accountPage.accountScreen
import com.app.tripup.presentation.mainFlow.explore.exploreMain.MainExploreDestination
import kotlinx.serialization.Serializable

@Serializable
data object AccountNavGraph

fun NavGraphBuilder.accountGraph(
    navController: NavController,
){
    navigation<AccountNavGraph>(
        startDestination = AccountDestination
    ) {
        accountScreen(
            onLogoutClick = {
                // Crear la instancia de LoginViewModel usando la Factory
                val loginViewModel: LoginViewModel = ViewModelProvider(navController.context as ViewModelStoreOwner, LoginViewModel.Factory)
                    .get(LoginViewModel::class.java)

                // Llamar al método de logout en LoginViewModel
                loginViewModel.onLogout()

                // Navegar a la pantalla de Login y vaciar el backstack
                navController.navigate(MainExploreDestination) { //LOGOUT DE MOMENTO NO ES FUNCIONAL TENGO QUE VER QUÉ ONDA
                    //MANDAR A EXPLORE DE MOMENTO
                    popUpTo(0)
                }
            }
        )
    }
}
