package com.app.tripup.addExplore

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.components.ItineraryCard
import com.app.tripup.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExploreScreen(
    itineraries: List<Pair<String, Boolean>>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    // Estado mutable para la lista de itinerarios (nombre y si está seleccionado)
    var itinerariesState by remember { mutableStateOf(itineraries) }

    // Verificar si alguno de los itinerarios está seleccionado
    val isAnySelected = itinerariesState.any { it.second }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* Acción de completar */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAnySelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = stringResource(id = R.string.add_to_itinerary_complete_button))
                }
            }

        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.add_to_itinerary_from_explore),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }

    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.add_to_itinerary_from_explore_indication),
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(8.dp)
            )

            // Renderizamos la lista de itinerarios
            itinerariesState.forEachIndexed { index, (itinerary, isSelected) ->
                ItineraryCard(
                    title = itinerary,
                    isSelected = isSelected,
                    onCardClick = {
                        // Actualizar el estado de selección del itinerario correspondiente
                        itinerariesState = itinerariesState.mapIndexed { i, pair ->
                            if (i == index) pair.copy(second = !isSelected) else pair
                        }
                    }
                )
            }
        }
    }
}

// Previews con listas simuladas de itinerarios
@Preview(showBackground = true)
@Composable
fun AddExploreScreenPreview_Selected_Light() {
    val itineraries = listOf(
        "Family Trip" to false,
        "Beach Vacation" to true, // Seleccionado
        "Mountain Hike" to false
    )

    MyApplicationTheme {
        AddExploreScreen(itineraries = itineraries)
    }
}

@Preview(showBackground = true)
@Composable
fun AddExploreScreenPreview_NotSelected_Light() {
    val itineraries = listOf(
        "Family Trip" to false,
        "Beach Vacation" to false,
        "Mountain Hike" to false
    )

    MyApplicationTheme {
        AddExploreScreen(itineraries = itineraries)
    }
}

@Preview(showBackground = true)
@Composable
fun AddExploreScreenPreview_Selected_Dark() {
    val itineraries = listOf(
        "Family Trip" to false,
        "Beach Vacation" to true, // Seleccionado
        "Mountain Hike" to false
    )

    MyApplicationTheme(darkTheme = true) {
        AddExploreScreen(itineraries = itineraries)
    }
}

@Preview(showBackground = true)
@Composable
fun AddExploreScreenPreview_NotSelected_Dark() {
    val itineraries = listOf(
        "Family Trip" to false,
        "Beach Vacation" to false,
        "Mountain Hike" to false
    )

    MyApplicationTheme(darkTheme = true) {
        AddExploreScreen(itineraries = itineraries)
    }
}
