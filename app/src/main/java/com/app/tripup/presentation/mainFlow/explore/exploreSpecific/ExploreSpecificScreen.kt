package com.app.tripup.presentation.mainFlow.explore.exploreSpecific


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.app.tripup.R
import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory
import com.app.tripup.data.source.PlaceDb
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun ExploreSpecificRoute(
    query: String,
    viewModel: ExploreSpecificViewModel = viewModel(),
    onPlaceClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Ejecuta la búsqueda solo una vez por término
    LaunchedEffect(query) {
        viewModel.searchPlaces(query)
    }

    ExploreSpecificScreen(
        query = query,
        state = state,
        onPlaceClick = onPlaceClick,
        onBackClick = onBackClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreSpecificScreen(
    query: String,
    state: ExploreSpecificState,
    onBackClick: () -> Unit = {},
    onPlaceClick: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = query, fontWeight = FontWeight.Bold) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.places.isNotEmpty() -> {
                    LazyColumn {
                        val groupedPlaces = state.places.groupBy { it.category }
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
                                    modifier = Modifier.padding(16.dp)
                                )

                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(placeList) { place ->
                                        PlaceCard(
                                            place = place,
                                            onClick = { onPlaceClick(place.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.notfound),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(250.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.noResultsMessage ?: "",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
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
                        color = MaterialTheme.colorScheme.surface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = place.location,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.surface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ExploreSpecificScreenPreview() {
    MyApplicationTheme {
        // Simulación de un estado con resultados
        val sampleState = ExploreSpecificState(
            isLoading = false,
            places = listOf(
                Place(
                    id = 1,
                    name = "Hotel Guatemala",
                    location = "Guatemala City",
                    imageUrl = "",
                    description = "Un hotel espectacular en Guatemala",
                    category = PlaceCategory.HOTELS,
                    isFavorite = false
                ),
                Place(
                    id = 2,
                    name = "Restaurante Antigua",
                    location = "Antigua, Guatemala",
                    imageUrl = "",
                    description = "Un restaurante con comida tradicional guatemalteca",
                    category = PlaceCategory.RESTAURANTS,
                    isFavorite = true
                )
            ),
            noResultsMessage = null
        )
        ExploreSpecificScreen(
            state = sampleState,
            onBackClick = {},
            onPlaceClick = {},
            query = "hola"

        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreSpecificScreenDarkPreview() {
    MyApplicationTheme(darkTheme = true) {
        // Simulación de un estado con resultados en modo oscuro
        val sampleState = ExploreSpecificState(
            isLoading = false,
            places = listOf(
                Place(
                    id = 1,
                    name = "Hotel Guatemala",
                    location = "Guatemala City",
                    imageUrl = "",
                    description = "Un hotel espectacular en Guatemala",
                    category = PlaceCategory.HOTELS,
                    isFavorite = false
                ),
                Place(
                    id = 2,
                    name = "Restaurante Antigua",
                    location = "Antigua, Guatemala",
                    imageUrl = "",
                    description = "Un restaurante con comida tradicional guatemalteca",
                    category = PlaceCategory.RESTAURANTS,
                    isFavorite = true
                )
            ),
            noResultsMessage = null
        )
        ExploreSpecificScreen(
            state = sampleState,
            onBackClick = {},
            onPlaceClick = {},
            query = "hola"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreSpecificScreenNoResultsPreview() {
    MyApplicationTheme {
        // Simulación de un estado sin resultados
        val sampleState = ExploreSpecificState(
            isLoading = false,
            places = emptyList(),
            noResultsMessage = "No se encontraron resultados para tu búsqueda"
        )
        ExploreSpecificScreen(
            state = sampleState,
            onBackClick = {},
            onPlaceClick = {},
            query = "hola"

        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreSpecificScreenNoResultsDarkPreview() {
    MyApplicationTheme(darkTheme = true) {
        // Simulación de un estado sin resultados en modo oscuro
        val sampleState = ExploreSpecificState(
            isLoading = false,
            places = emptyList(),
            noResultsMessage = "No se encontraron resultados para tu búsqueda"
        )
        ExploreSpecificScreen(
            state = sampleState,
            onBackClick = {},
            onPlaceClick = {},
            query = "hola"

        )
    }
}
