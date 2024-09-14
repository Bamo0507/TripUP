package com.app.tripup.exploreSpecific


import android.content.res.Configuration
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.app.tripup.R
import com.app.tripup.data.Place
import com.app.tripup.data.PlaceCategory
import com.app.tripup.data.PlaceDb
import com.app.tripup.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreSpecificScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val queryText = "Earth"
    val places = PlaceDb().getAllPlaces()
    val groupedPlaces = places.groupBy { it.category }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = queryText,
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
                .padding(innerPadding)
        ) {
            // Box con fondo onSurfaceVariant
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.search_no_results),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // Estructura de LazyColumn y LazyRow como en ExploreScreen
            LazyColumn {
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
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(placeList) { place ->
                                PlaceCard(
                                    place,
                                    modifier = Modifier
                                        .width(200.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceCard(place: Place, modifier: Modifier = Modifier) {
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
            .clickable { /* Manejar clic */ },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Contenido de texto con fondo semitransparente
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
fun ExploreSpecificScreenPreview() {
    MyApplicationTheme {
        ExploreSpecificScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreSpecificScreenPreviewDarkMode() {
    MyApplicationTheme {
        ExploreSpecificScreen()
    }
}

