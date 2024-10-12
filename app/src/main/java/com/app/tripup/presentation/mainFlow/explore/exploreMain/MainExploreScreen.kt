package com.app.tripup.presentation.mainFlow.explore.exploreMain

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.tripup.R
import com.app.tripup.presentation.navigation.BottomNavigationBar // Importa el BottomNavigationBar desde el paquete components
import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory
import com.app.tripup.data.source.PlaceDb
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun MainExploreRoute(
    viewModel: MainExploreViewModel = viewModel(),
    onPlaceClick: (Int) -> Unit,
    onNavigateToSpecific: (String) -> Unit // Agregar este parámetro para la búsqueda
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ExploreScreen(
        state = state,
        onPlaceClick = onPlaceClick,
        onSearch = { query ->
            viewModel.onSearchQuerySubmitted(query) {
                onNavigateToSpecific(it) // Redirigir a ExploreSpecificScreen con el término de búsqueda
            }
        }
    )
}


@Composable
fun ExploreScreen(
    state: MainExploreState,
    onPlaceClick: (Int) -> Unit,
    onSearch: (String) -> Unit // Agregamos el evento de búsqueda
) {
    var query by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de búsqueda con el evento de búsqueda
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { onSearch(query) } // Llamamos a la búsqueda cuando se presiona "Enter" o se hace clic
            )

            // Mensaje de "Explora cerca de ti"
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.explore_near_you),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }

            LazyColumn {
                val groupedPlaces = state.data.groupBy { it.category }
                groupedPlaces.forEach { (category, placeList) ->
                    item {
                        Text(
                            text = when (category) {
                                PlaceCategory.RESTAURANTS -> stringResource(id = R.string.category_restaurants)
                                PlaceCategory.HOTELS -> stringResource(id = R.string.category_hotels)
                                PlaceCategory.DRINKS -> stringResource(id = R.string.category_drinks)
                                PlaceCategory.ACTIVITIES -> stringResource(id = R.string.category_activities)
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(placeList) { place ->
                                PlaceCard(
                                    place,
                                    onClick = { onPlaceClick(place.id) },
                                    modifier = Modifier.width(200.dp)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit // Función para manejar el evento de búsqueda
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_placeholder),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Normal
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch() // Al presionar "Enter" realiza la búsqueda
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )
        }
    }
}

@Composable
fun PlaceCard(place: Place, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val backgroundColor = when (place.category) {
        PlaceCategory.RESTAURANTS -> MaterialTheme.colorScheme.primary
        PlaceCategory.HOTELS -> MaterialTheme.colorScheme.secondary
        PlaceCategory.DRINKS -> MaterialTheme.colorScheme.tertiary
        PlaceCategory.ACTIVITIES -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .size(200.dp)
            .padding(8.dp)
            .clickable(onClick = onClick), // Manejar clic aquí
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.surface
                    )
                    Text(
                        text = place.location,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    MyApplicationTheme {
        val sampleState = MainExploreState(
            isLoading = false,
            data = listOf(
                Place(
                    id = 1,
                    name = "Place 1",
                    location = "Location 1",
                    imageUrl = "",
                    description = "Description of Place 1",
                    category = PlaceCategory.RESTAURANTS
                ),
                Place(
                    id = 2,
                    name = "Place 2",
                    location = "Location 2",
                    imageUrl = "",
                    description = "Description of Place 2",
                    category = PlaceCategory.HOTELS
                )
            )
        )
        ExploreScreen(state = sampleState, onPlaceClick = {}, onSearch = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreScreenPreviewDark() {
    MyApplicationTheme {
        val sampleState = MainExploreState(
            isLoading = false,
            data = listOf(
                Place(
                    id = 1,
                    name = "Place 1",
                    location = "Location 1",
                    imageUrl = "",
                    description = "Description of Place 1",
                    category = PlaceCategory.RESTAURANTS
                ),
                Place(
                    id = 2,
                    name = "Place 2",
                    location = "Location 2",
                    imageUrl = "",
                    description = "Description of Place 2",
                    category = PlaceCategory.HOTELS
                )
            )
        )
        ExploreScreen(state = sampleState, onPlaceClick = {}, onSearch = {})
    }
}
