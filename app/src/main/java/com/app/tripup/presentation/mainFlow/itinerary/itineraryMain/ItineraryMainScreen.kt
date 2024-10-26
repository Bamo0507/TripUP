package com.app.tripup.presentation.mainFlow.itinerary.itineraryMain

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.format.DateUtils.formatDateRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.app.tripup.data.local.entities.Itinerary
import com.app.tripup.data.repository.ItineraryRepository
import java.text.SimpleDateFormat
import java.util.Locale

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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                onClick = onCreateItinerary,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (itineraries.isEmpty()) {
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
                            text = stringResource(id = R.string.no_itineraries),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp)
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
}

@Composable
fun ItineraryItem(
    itinerary: Itinerary,
    onClick: () -> Unit
) {
    Surface(
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 13.dp)
            .clickable(onClick = onClick),
        tonalElevation = 30.dp,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = itinerary.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Date Range",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = formatDateRange(itinerary.startDate, itinerary.endDate),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDateRange(startDate: String, endDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM d", Locale.getDefault()) // Ej: October 10

    val start = inputFormat.parse(startDate)
    val end = inputFormat.parse(endDate)

    return if (start != null && end != null) {
        "${outputFormat.format(start)} - ${outputFormat.format(end)}"
    } else {
        "$startDate - $endDate" // Fallback en caso de error
    }
}


@Preview(showBackground = true)
@Composable
fun ItineraryMainScreenPreview() {
    val sampleItineraries = listOf(
        Itinerary(id = 1, name = "Vacation", startDate = "2024-11-01", endDate = "2024-11-10"),
        Itinerary(id = 2, name = "Business Trip", startDate = "2024-12-01", endDate = "2024-12-05")
    )

    ItineraryMainScreen(
        itineraries = sampleItineraries,
        onItinerarySelected = {},
        onCreateItinerary = {}
    )
}
