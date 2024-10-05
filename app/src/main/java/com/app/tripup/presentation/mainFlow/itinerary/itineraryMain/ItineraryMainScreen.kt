package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.presentation.ui.theme.MyApplicationTheme


@Composable
fun JourneyListScreen(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
            }
        },
        bottomBar = {
            BottomNavigationBar(selectedItem = selectedItem, onItemSelected = onItemSelected)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            ItineraryCardWithColorBox(
                title = stringResource(id = R.string.itinerary_title_example), //Más adelante se agregará la función adecuada
                //Se tendrá una carta por cada una de las cards que el usuario tenga, es decir, por cada itinerario
                isSelected = false,
                onCardClick = { /* Acción al hacer clic en la tarjeta */ }
            )
        }
    }
}


@Composable
fun ItineraryCardWithColorBox(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onCardClick() }
            .shadow(24.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.icon_example),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
            }


            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            )
        }
    }
}


@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        NavigationItem.Explore,
        NavigationItem.Itinerary,
        NavigationItem.Account
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedItem == index

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(index) },
                icon = {
                    if (isSelected) {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondaryContainer),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary
                            ),
                            modifier = Modifier
                                .size(width = 80.dp, height = 40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

sealed class NavigationItem(val icon: ImageVector, val title: Int) {
    object Explore : NavigationItem(Icons.Default.Explore, R.string.nav_explore)
    object Itinerary : NavigationItem(Icons.Default.Commute, R.string.nav_itinerary)
    object Account : NavigationItem(Icons.Default.Person, R.string.nav_account)
}

@Preview(showBackground = true)
@Composable
fun PreviewJourneyListScreen() {
    MyApplicationTheme {
        JourneyListScreen(selectedItem = 1, onItemSelected = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewJourneyListScreenDarkMode() {
    MyApplicationTheme {
        JourneyListScreen(selectedItem = 1, onItemSelected = {})
    }
}

