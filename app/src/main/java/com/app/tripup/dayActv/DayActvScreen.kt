package com.app.tripup.dayActv

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
fun CreateActivityScreen(
    activityName: String = "",
    startTime: String = "",
    endTime: String = "",
    onBackClick: () -> Unit = {}
) {

    var activityNameState by remember { mutableStateOf(activityName) }
    var startTimeState by remember { mutableStateOf(startTime) }
    var endTimeState by remember { mutableStateOf(endTime) }

    Scaffold(
        topBar = {
            ActivityTopAppBar(onBackClick = onBackClick)
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

                ActivityTextField(
                    value = activityNameState,
                    label = "Activity Name",
                    placeholder = "Input",
                    onValueChange = { activityNameState = it }
                )

                Text(
                    text = "What are we doing?",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    ActivityTextField(
                        value = startTimeState,
                        label = "Start time",
                        placeholder = "8:00 AM",
                        onValueChange = { startTimeState = it },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    ActivityTextField(
                        value = endTimeState,
                        label = "End time",
                        placeholder = "8:00 AM",
                        onValueChange = { endTimeState = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }


            ActivityCompleteButton(
                isFormComplete = activityNameState.isNotEmpty() && startTimeState.isNotEmpty() && endTimeState.isNotEmpty(),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Family Trip/August 5") },
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
fun ActivityTextField(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = modifier.fillMaxWidth(),
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
fun ActivityCompleteButton(isFormComplete: Boolean, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Acción del botón */ },
        modifier = modifier
            .padding(16.dp)
            .width(140.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFormComplete) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
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
    CreateActivityScreen(
        activityName = "",
        startTime = "",
        endTime = ""
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAfterFilled() {
    CreateActivityScreen(
        activityName = "UNO's Restaurant",
        startTime = "8:00 AM",
        endTime = "7:00 PM"
    )
}
