package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun DatesListScreen(
    onBackClick: () -> Unit = {},
    onDateClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            DatesTopAppBar(onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            //más adelante se aplicará la lógica necesaria
            //se tendrá un día por cada uno de los días que se encuentren en el intervalo de fechas
            val dates = listOf(
                stringResource(R.string.Day1_example),
                stringResource(R.string.Day2_example),
                stringResource(R.string.Day3_example),
                stringResource(R.string.Day4_example),
                stringResource(R.string.Day5_example),
                stringResource(R.string.Day6_example)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                dates.forEach { date ->
                    ItineraryCard(
                        title = date,
                        isSelected = false,
                        onCardClick = { onDateClick(date) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatesTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.itinerary_title_example)) },
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

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                color = textColor
            )

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
fun PreviewDatesListScreen() {
    MyApplicationTheme {
        DatesListScreen()

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDatesListScreenDark() {
    MyApplicationTheme {
        DatesListScreen()

    }
}

