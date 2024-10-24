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
    context: android.content.Context,  // Recibir el contexto como parámetro
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
                title = { Text("$itineraryTitle / $date") },
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
                value = activityName,
                onValueChange = onActivityNameChanged,
                label = { Text("Activity Name") },
                trailingIcon = {
                    if (activityName.isNotEmpty()) {
                        IconButton(onClick = { onActivityNameChanged("") }) {
                            Icon(Icons.Filled.Close, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Start Time Field
            OutlinedTextField(
                value = startTime,
                onValueChange = {},
                label = { Text("Start Time") },
                trailingIcon = {
                    IconButton(onClick = {
                        showTimePickerDialog(context) { time ->
                            onStartTimeChanged(time)
                        }
                    }) {
                        Icon(Icons.Default.AccessTime, contentDescription = "Select Start Time")
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // End Time Field (Usar endTime correctamente)
            OutlinedTextField(
                value = endTime,  // Cambiado a endTime
                onValueChange = {},
                label = { Text("End Time") },
                trailingIcon = {
                    IconButton(onClick = {
                        showTimePickerDialog(context) { time ->
                            onEndTimeChanged(time)  // Cambiado a onEndTimeChanged
                        }
                    }) {
                        Icon(Icons.Default.AccessTime, contentDescription = "Select End Time")
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveActivity,
                enabled = isFormComplete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Activity")
            }
        }
    }
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
