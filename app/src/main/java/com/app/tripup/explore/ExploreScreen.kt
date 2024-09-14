package com.app.tripup.explore

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.app.tripup.R
import com.app.tripup.components.BottomNavigationBar // Importa el BottomNavigationBar desde el paquete components
import com.app.tripup.data.Place
import com.app.tripup.data.PlaceCategory
import com.app.tripup.data.PlaceDb
import com.app.tripup.ui.theme.MyApplicationTheme

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    val places = PlaceDb().getAllPlaces()
    val groupedPlaces = places.groupBy { it.category }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = 0,
                onItemSelected = { /* Manejar clic en la barra de navegación */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de búsqueda
            SearchBar()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }

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
                onValueChange = { query = it },
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
                )
            )
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
fun ExploreScreenPreview() {
    MyApplicationTheme {
        ExploreScreen(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExploreScreenPreviewDark() {
    MyApplicationTheme {
        ExploreScreen(modifier = Modifier.fillMaxSize())
    }
}

