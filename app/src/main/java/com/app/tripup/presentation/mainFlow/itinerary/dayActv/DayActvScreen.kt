package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.R
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.local.entities.Activity
import com.app.tripup.data.repository.ActivityRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                title = {
                    Text(
                        "$itineraryTitle / ${formatDate(date)}",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddActivityClick,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Activity")
            }
        }
    ) { paddingValues ->
        if (activities.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),  // Espaciado general más consistente
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Imagen
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp),  // Espacio entre la imagen y el texto
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.notfound),
                            contentDescription = null,  // Accesibilidad (opcional si es decorativa)
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(250.dp)
                        )
                    }

                    // Texto
                    Text(
                        text = stringResource(id = R.string.no_activities),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
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

fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d", Locale.getDefault())
    val parsedDate = LocalDate.parse(dateString)
    return parsedDate.format(formatter)
}


@Composable
fun ActivityItem(
    activity: Activity
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { /* Handle activity click */ },
        shadowElevation = 4.dp,
        tonalElevation = 6.dp,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = activity.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Activity Time",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${activity.startTime} - ${activity.endTime}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDayActivityScreen() {
    val sampleActivities = listOf(
        Activity(dayItineraryId = 1, name = "Breakfast", startTime = "08:00", endTime = "09:00"),
        Activity(dayItineraryId = 2, name = "City Tour", startTime = "10:00", endTime = "12:00"),
        Activity(dayItineraryId = 3, name = "Lunch", startTime = "13:00", endTime = "14:00"),
        Activity(dayItineraryId = 4, name = "Museum Visit", startTime = "15:00", endTime = "17:00")
    )

    DayActivityScreen(
        itineraryTitle = "Paris Trip",
        date = "2024-10-24",
        activities = sampleActivities,
        onAddActivityClick = { /* TODO: Handle Add Activity */ },
        onBackClick = { /* TODO: Handle Back */ }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDayActivityScreenEmpty() {
    val sampleActivities = emptyList<Activity>()

    DayActivityScreen(
        itineraryTitle = "Paris Trip",
        date = "2024-10-24",
        activities = sampleActivities,
        onAddActivityClick = { /* TODO: Handle Add Activity */ },
        onBackClick = { /* TODO: Handle Back */ }
    )
}
