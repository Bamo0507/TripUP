// DayActivityScreen.kt
package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.repository.ActivityRepository

@Composable
fun DayActivityRoute(
    dayItineraryId: Int,
    itineraryTitle: String,
    date: String,
    onAddActivityClick: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val activityRepository = ActivityRepository(
        DatabaseModule.getDatabase(context).activityDao()
    )
    val viewModel: DayActivityViewModel = viewModel(
        factory = DayActivityViewModelFactory(activityRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    // Iniciamos la carga de datos
    LaunchedEffect(Unit) {
        viewModel.loadActivities(dayItineraryId)
    }

    DayActivityScreen(
        itineraryTitle = itineraryTitle,
        date = date,
        activities = uiState.activities,
        onAddActivityClick = { onAddActivityClick(dayItineraryId, itineraryTitle, date) },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayActivityScreen(
    itineraryTitle: String,
    date: String,
    activities: List<Activity>,
    onAddActivityClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$itineraryTitle / $date") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddActivityClick
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        if (activities.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No activities found.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(activities) { activity ->
                    ActivityItem(
                        activity = activity
                    )
                }
            }
        }
    }
}
@Composable
fun ActivityItem(
    activity: Activity
) {
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineContent = { Text(activity.name) },
        supportingContent = { Text("${activity.startTime} - ${activity.endTime}") }
    )
}
