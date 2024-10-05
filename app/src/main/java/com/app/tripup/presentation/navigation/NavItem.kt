package com.app.tripup.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Commute
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.tripup.presentation.mainFlow.explore.exploreMain.MainExploreDestination

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Any,
)

val navigationItems = listOf(
    NavItem(
        title = "Explore",
        selectedIcon = Icons.Filled.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        destination = MainExploreDestination
    ),

    NavItem(
        title = "Itinerary",
        selectedIcon = Icons.Filled.Commute,
        unselectedIcon = Icons.Outlined.Commute,
        destination = "itinerary" //REEMPLAZAR CON EL SERIALIZABLE OBJECT CUANDO SE TENGA
    ),

    NavItem(
        title = "Account",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        destination = "account" //REEMPLAZAR CON EL SERIALIZABLE OBJECT CUANDO SE TENGA
    )

)

val topLevelDestinations = listOf(
    MainExploreDestination::class,
    //Aquí se colocarán las pantallas conforme se tengan
)