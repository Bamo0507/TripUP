package com.app.tripup.itineraryCreation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateJourneyScreen(
    title: String = "",
    startTime: String = "",
    onBackClick: () -> Unit = {}
) {
    var titleState by remember { mutableStateOf(title) }
    var startTimeState by remember { mutableStateOf(startTime) }

    Scaffold(
        topBar = {
            JourneyTopAppBar(onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Campo de texto Itinerary Title
                JourneyTextField(
                    value = titleState,
                    label = "Itinerary Title",
                    placeholder = "Input",
                    onValueChange = { titleState = it }
                )

                Text(
                    text = "Give your trip a memorable name",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


                JourneyTextField(
                    value = startTimeState,
                    label = "Start time",
                    placeholder = "Input",
                    onValueChange = { startTimeState = it }
                )
            }

            // Botón  de "Complete"
            JourneyCompleteButton(
                isFormComplete = titleState.isNotEmpty() && startTimeState.isNotEmpty(),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Create Your Journey") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyTextField(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
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

@Composable
fun JourneyCompleteButton(isFormComplete: Boolean, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Acción del botón */ },
        modifier = modifier
            .padding(16.dp)
            .width(140.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFormComplete) {
                MaterialTheme.colorScheme.primary // Si el formulario está completo
            } else {
                MaterialTheme.colorScheme.secondary // Si el formulario no está completo
            }
        ),
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Check, contentDescription = "Check icon")
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = "Complete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBeforeFilled() {
    CreateJourneyScreen(
        title = "", 
        startTime = ""
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAfterFilled() {
    CreateJourneyScreen(
        title = "Family Trip",
        startTime = "08/16/2024 - 08/27/2024"
    )
}

