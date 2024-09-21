package com.app.tripup.dayActv

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.tripup.R
import com.app.tripup.ui.theme.MyApplicationTheme

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
                    label = stringResource(id = R.string.activity_name),
                    placeholder = "Input",
                    onValueChange = { activityNameState = it }
                )

                Text(
                    text = stringResource(id = R.string.phrase_actv),
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
                        label = stringResource(id = R.string.start_time),
                        placeholder = "8:00 AM",
                        onValueChange = { startTimeState = it },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    ActivityTextField(
                        value = endTimeState,
                        label = stringResource(id = R.string.end_time),
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
        //De momento se dejará así más adelante se implementará la lógica
        //El título que se muestra dependerá de lo que el usuario haya seleccionado en la pantalla anterior
        title = { Text(text = "Family Trip/August 5") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
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
                MaterialTheme.colorScheme.tertiaryContainer
            }
        ),
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Check, contentDescription = "Check icon",
                tint = if (isFormComplete) MaterialTheme.colorScheme.onPrimary else
                    MaterialTheme.colorScheme.onTertiaryContainer)
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = stringResource(id = R.string.complete_button),
                color = if (isFormComplete) MaterialTheme.colorScheme.onPrimary else
                    MaterialTheme.colorScheme.onTertiaryContainer
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBeforeFilledLight() {
    MyApplicationTheme {
        CreateActivityScreen(
            activityName = "",
            startTime = "",
            endTime = ""
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewAfterFilledLight() {
    MyApplicationTheme {
        CreateActivityScreen(
            activityName = "UNO's Restaurant",
            startTime = "8:00 AM",
            endTime = "7:00 PM"
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBeforeFilledDark() {
    MyApplicationTheme {
        CreateActivityScreen(
            activityName = "",
            startTime = "",
            endTime = ""
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAfterFilledDark() {
    MyApplicationTheme {
        CreateActivityScreen(
            activityName = "UNO's Restaurant",
            startTime = "8:00 AM",
            endTime = "7:00 PM"
        )
    }
}
