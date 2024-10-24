// ItineraryCreationScreen.kt
package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.repository.DayItineraryRepository
import com.app.tripup.data.repository.ItineraryRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ItineraryCreationRoute(
    onItineraryCreated: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val itineraryRepository = ItineraryRepository(
        DatabaseModule.getDatabase(context).itineraryDao()
    )
    val dayItineraryRepository = DayItineraryRepository(
        DatabaseModule.getDatabase(context).dayItineraryDao()
    )
    val viewModel: ItineraryCreationViewModel = viewModel(
        factory = ItineraryCreationViewModelFactory(itineraryRepository, dayItineraryRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    // Handle navigation when the itinerary is saved
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            uiState.itineraryId?.let { id ->
                onItineraryCreated(id.toInt())
            }
        }
    }

    ItineraryCreationScreen(
        title = uiState.title,
        startDate = uiState.startDate,
        endDate = uiState.endDate,
        onTitleChanged = viewModel::onTitleChanged,
        onStartDateChanged = viewModel::onStartDateChanged,
        onEndDateChanged = viewModel::onEndDateChanged,
        onSaveItinerary = viewModel::onSaveItinerary,
        isFormComplete = uiState.isFormComplete,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryCreationScreen(
    title: String,
    startDate: String,
    endDate: String,
    onTitleChanged: (String) -> Unit,
    onStartDateChanged: (String) -> Unit,
    onEndDateChanged: (String) -> Unit,
    onSaveItinerary: () -> Unit,
    isFormComplete: Boolean,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Itinerary") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChanged,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            DatePickerField(
                label = "Start Date",
                date = startDate,
                onDateSelected = onStartDateChanged
            )
            Spacer(modifier = Modifier.height(16.dp))
            DatePickerField(
                label = "End Date",
                date = endDate,
                onDateSelected = onEndDateChanged
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSaveItinerary,
                enabled = isFormComplete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Complete")
            }
        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    date: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateState = remember { mutableStateOf(date) }

    OutlinedTextField(
        value = dateState.value,
        onValueChange = {},
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = {
                val initialDate = if (dateState.value.isNotEmpty()) {
                    LocalDate.parse(dateState.value, formatter)
                } else {
                    LocalDate.now()
                }
                showDatePickerDialog(
                    context = context,
                    initialDate = initialDate,
                    onDateSelected = { selectedDate ->
                        val formattedDate = selectedDate.format(formatter)
                        dateState.value = formattedDate
                        onDateSelected(formattedDate)
                    }
                )
            }) {
                Icon(Icons.Filled.CalendarToday, contentDescription = "Select Date")
            }
        },
        readOnly = true,
        modifier = Modifier.fillMaxWidth()
    )
}

fun showDatePickerDialog(
    context: android.content.Context,
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        initialDate.year,
        initialDate.monthValue - 1,
        initialDate.dayOfMonth
    )
    datePickerDialog.show()
}
