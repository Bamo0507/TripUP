// DayInfoScreen.kt
package com.app.tripup.presentation.mainFlow.itinerary.addInfoDay

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.repository.ActivityRepository
import java.util.Calendar
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.app.tripup.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DayInfoRoute(
    dayItineraryId: Int,
    itineraryTitle: String,
    date: String,
    onActivityCreated: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current // Obtener contexto aquí

    val activityRepository = ActivityRepository(
        DatabaseModule.getDatabase(context).activityDao()
    )
    val viewModel: DayInfoViewModel = viewModel(
        factory = DayInfoViewModelFactory(activityRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    DayInfoScreen(
        itineraryTitle = itineraryTitle,
        date = date,
        context = context,  // Pasar el contexto a la pantalla
        activityName = uiState.activityName,
        startTime = uiState.startTime,
        endTime = uiState.endTime,
        onActivityNameChanged = viewModel::onActivityNameChanged,
        onStartTimeChanged = viewModel::onStartTimeChanged,
        onEndTimeChanged = viewModel::onEndTimeChanged,
        onSaveActivity = {
            viewModel.onSaveActivity(dayItineraryId)
            onActivityCreated()
        },
        isFormComplete = uiState.isFormComplete,
        onBackClick = onBackClick
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayInfoScreen(
    itineraryTitle: String,
    date: String,
    context: android.content.Context,
    activityName: String,
    startTime: String,
    endTime: String,
    onActivityNameChanged: (String) -> Unit,
    onStartTimeChanged: (String) -> Unit,
    onEndTimeChanged: (String) -> Unit,
    onSaveActivity: () -> Unit,
    isFormComplete: Boolean,
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nombre de la actividad
            OutlinedTextField(
                value = activityName,
                onValueChange = onActivityNameChanged,
                label = { Text(stringResource(id = R.string.activity_name)) },
                placeholder = { Text(stringResource(id = R.string.activity_placeholder)) },
                trailingIcon = {
                    if (activityName.isNotEmpty()) {
                        IconButton(onClick = { onActivityNameChanged("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Selección de Hora de Inicio
            TimePickerField(
                label = stringResource(id = R.string.start_time),
                time = startTime,
                onTimeSelected = onStartTimeChanged,
                context = context
            )

            // Selección de Hora de Fin
            TimePickerField(
                label = stringResource(id = R.string.end_time),
                time = endTime,
                onTimeSelected = onEndTimeChanged,
                context = context
            )

            // Botón de Guardar Actividad
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = onSaveActivity,
                    enabled = isFormComplete,
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Check, contentDescription = "Save")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(id = R.string.activity_save))
                    }
                }
            }
        }
    }
}

@Composable
fun TimePickerField(
    label: String,
    time: String,
    onTimeSelected: (String) -> Unit,
    context: android.content.Context
) {
    OutlinedTextField(
        value = time,
        onValueChange = {},
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = {
                showTimePickerDialog(context) { selectedTime ->
                    onTimeSelected(selectedTime)
                }
            }) {
                Icon(Icons.Default.AccessTime, contentDescription = "Select Time",
                    tint = MaterialTheme.colorScheme.primary)
            }
        },
        readOnly = true,
        modifier = Modifier.fillMaxWidth()
    )
}

fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d", Locale.getDefault())
    val parsedDate = LocalDate.parse(dateString)
    return parsedDate.format(formatter)
}

fun showTimePickerDialog(
    context: android.content.Context,
    onTimeSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeSelected(formattedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    timePickerDialog.show()
}

@Preview(showBackground = true)
@Composable
fun DayInfoScreenPreview() {
    DayInfoScreen(
        itineraryTitle = "Paris Trip",
        date = "2024-10-24",
        context = LocalContext.current,
        activityName = "Lunch",
        startTime = "12:00",
        endTime = "13:00",
        onActivityNameChanged = {},
        onStartTimeChanged = {},
        onEndTimeChanged = {},
        onSaveActivity = {},
        isFormComplete = false,
        onBackClick = {}
    )
}
