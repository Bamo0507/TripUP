package com.app.tripup.presentation.mainFlow.account.accountPage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AccountDestination

fun NavGraphBuilder.accountScreen(
    onLogoutClick: () -> Unit
){
    composable<AccountDestination>{
        AccountRoute(
            onLogoutClick = onLogoutClick
        )
    }
}