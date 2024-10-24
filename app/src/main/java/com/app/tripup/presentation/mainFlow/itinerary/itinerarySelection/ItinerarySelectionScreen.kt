// ItinerarySelectionScreen.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.local.entities.DayItinerary
import com.app.tripup.data.repository.DayItineraryRepository
import com.app.tripup.data.repository.ItineraryRepository

@Composable
fun ItinerarySelectionRoute(
    itineraryId: Int,
    onDaySelected: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val dayItineraryRepository = DayItineraryRepository(
        DatabaseModule.getDatabase(context).dayItineraryDao()
    )
    val itineraryRepository = ItineraryRepository(
        DatabaseModule.getDatabase(context).itineraryDao()
    )
    val viewModel: ItinerarySelectionViewModel = viewModel(
        factory = ItinerarySelectionViewModelFactory(dayItineraryRepository, itineraryRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDays(itineraryId)
    }

    ItinerarySelectionScreen(
        itineraryTitle = uiState.itineraryTitle,
        days = uiState.dayItineraries,
        onDaySelected = onDaySelected,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItinerarySelectionScreen(
    itineraryTitle: String,
    days: List<DayItinerary>,
    onDaySelected: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(itineraryTitle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (days.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No days found.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(days) { day ->
                    DayItem(
                        dayItinerary = day,
                        onClick = {
                            onDaySelected(day.id, itineraryTitle, day.date)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DayItem(
    dayItinerary: DayItinerary,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        headlineContent = { Text("Day: ${dayItinerary.date}") }
    )
}