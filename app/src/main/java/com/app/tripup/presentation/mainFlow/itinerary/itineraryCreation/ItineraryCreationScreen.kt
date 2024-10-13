package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.presentation.ui.theme.MyApplicationTheme
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.Instant
import java.time.ZoneId

@Composable
fun ItineraryCreationRoute(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    CreateItineraryScreen(onBackClick = onBackClick,
        onCompleteClick = onCompleteClick)
}

@Composable
fun CreateItineraryScreen(
    viewModel: CreateItineraryViewModel = viewModel(),
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    val title by viewModel.title
    val startDate by viewModel.startDate
    val endDate by viewModel.endDate

    var showDatePicker by remember { mutableStateOf(false) }

    // Formato para mostrar la fecha seleccionada
    val dateRangeText = if (startDate != null && endDate != null) {
        "${startDate.toString()} - ${endDate.toString()}"
    } else {
        "Select Date Range"
    }

    Scaffold(
        topBar = {
            JourneyTopAppBar(onBackClick = onBackClick)
        },
        floatingActionButton = {
            JourneyCompleteButton(
                isFormComplete = title.isNotEmpty() && startDate != null && endDate != null,
                onClick = onCompleteClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ItineraryTitleField(
                title = title,
                onTitleChanged = { viewModel.onTitleChanged(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DateRangeSelector(
                dateRangeText = dateRangeText,
                onClick = { showDatePicker = true }
            )
        }
    }

    // Mover el diálogo fuera del Scaffold
    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { dateRange ->
                val startDateMillis = dateRange.first
                val endDateMillis = dateRange.second

                if (startDateMillis != null && endDateMillis != null) {
                    val start = Instant.ofEpochMilli(startDateMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    val end = Instant.ofEpochMilli(endDateMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    viewModel.onDatesSelected(start, end)
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryTitleField(
    title: String,
    onTitleChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChanged,
        label = { Text("Itinerary Title") },
        placeholder = { Text("Input") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (title.isNotEmpty()) {
                IconButton(onClick = { onTitleChanged("") }) {
                    Icon(Icons.Filled.Close, contentDescription = "Clear text")
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.medium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeSelector(
    dateRangeText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Altura similar a un campo de texto
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = dateRangeText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "Select date range",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss() // Cerrar el diálogo después de seleccionar la fecha
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                title = { Text("Select Date Range") },
                showModeToggle = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun JourneyCompleteButton(isFormComplete: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .width(140.dp)
            .height(48.dp),
        enabled = isFormComplete,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFormComplete) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            }
        ),
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.Check, contentDescription = "Check icon",
                tint = if (isFormComplete) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = "Complete",
                color = if (isFormComplete) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Create Journey") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}