package com.app.tripup.presentation.mainFlow.itinerary.addInfoDay

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun ActivityListScreen(
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ActivityTopAppBar(onBackClick = onBackClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            ItineraryCard(
                title = stringResource(id = R.string.activity_name_example),
                startTime = stringResource(id = R.string.start_time_example),
                endTime = stringResource(id = R.string.end_time_example),
                isSelected = false,
                onCardClick = { /* Acción al hacer clic en la tarjeta */ }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ItineraryCard(
                title = stringResource(id = R.string.activity_name_example_2),
                startTime = stringResource(id = R.string.start_time_example_2),
                endTime = stringResource(id = R.string.end_time_example_2),
                isSelected = false,
                onCardClick = { /* Acción al hacer clic en la tarjeta */ }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        //No se agrega a Strings ya que es algo variable que viene de pantalla anterior
        title = { Text(text = stringResource(id = R.string.list_of_activities_title)) },
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


@Composable
fun ItineraryCard(
    modifier: Modifier = Modifier,
    title: String,
    startTime: String,
    endTime: String,
    isSelected: Boolean = false,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onCardClick() }
            .shadow(24.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseOnSurface
        val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$startTime - $endTime",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            SolidColorBox(color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SolidColorBox(color: Color) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .width(75.dp)
            .background(color = color, shape = RoundedCornerShape(16.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewActivityListScreen() {
    MyApplicationTheme {
        ActivityListScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewActivityListScreenDarkMode() {
    MyApplicationTheme {
        ActivityListScreen()
    }
}
