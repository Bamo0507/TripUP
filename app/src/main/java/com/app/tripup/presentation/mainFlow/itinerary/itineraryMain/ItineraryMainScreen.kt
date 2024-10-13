package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.tripup.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun ItineraryMainRoute(
    viewModel: ItineraryMainViewModel = viewModel(),
    onItemSelected: (Int) -> Unit,
    onEditClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getListItineraries()
    }

    JourneyListScreen(viewModel = viewModel, onItemSelected = onItemSelected, onEditClick = onEditClick)
}


@Composable
fun JourneyListScreen(
    viewModel: ItineraryMainViewModel,
    onItemSelected: (Int) -> Unit,
    onEditClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.data.isEmpty()) {
                Text(text = "No itineraries available.")
            } else {
                LazyColumn {
                    // Usamos el tamaño de la lista de itinerarios
                    items(state.data.size) { index ->
                        val itinerary = state.data[index] // Obtener el itinerario en el índice correspondiente
                        ItineraryCardWithColorBox(
                            title = itinerary.name, // Acceder a 'name' correctamente
                            isSelected = false,
                            onCardClick = { onItemSelected(itinerary.id) } // Acceder a 'id' correctamente
                        )
                    }
                }
            }
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


