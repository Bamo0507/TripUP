// ItineraryMainScreen.kt
package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.local.entities.Itinerary
import com.app.tripup.data.repository.ItineraryRepository

@Composable
fun ItineraryMainRoute(
    onItinerarySelected: (Int) -> Unit,
    onCreateItinerary: () -> Unit
) {
    val context = LocalContext.current
    val itineraryRepository = ItineraryRepository(
        DatabaseModule.getDatabase(context).itineraryDao()
    )
    val viewModel: ItineraryMainViewModel = viewModel(
        factory = ItineraryMainViewModelFactory(itineraryRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    ItineraryMainScreen(
        itineraries = uiState.itineraries,
        onItinerarySelected = onItinerarySelected,
        onCreateItinerary = onCreateItinerary
    )
}

@Composable
fun ItineraryMainScreen(
    itineraries: List<Itinerary>,
    onItinerarySelected: (Int) -> Unit,
    onCreateItinerary: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateItinerary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        if (itineraries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No itineraries found.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(itineraries) { itinerary ->
                    ItineraryItem(
                        itinerary = itinerary,
                        onClick = { onItinerarySelected(itinerary.id) }
                    )
                }
            }
        }
    }
}
@Composable
fun ItineraryItem(
    itinerary: Itinerary,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        headlineContent = { Text(itinerary.name) },
        supportingContent = { Text("${itinerary.startDate} - ${itinerary.endDate}") }
    )
}