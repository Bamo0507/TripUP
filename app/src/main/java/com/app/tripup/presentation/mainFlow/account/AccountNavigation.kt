package com.app.tripup.presentation.mainFlow.account

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    ){
        //Lógica para logout pasada
        //PUEDE QUE TOQUE AGREGAR MÁS COSAS ACÁ
        accountScreen(
            onLogoutClick = {
                navController.navigate(LoginDestination) {
                    popUpTo(0)
                }
            }
        )
        //AQUÍ ABAJO VA LA PANTALLA DE FAVORITOS SU NAVIGATION



    }
}