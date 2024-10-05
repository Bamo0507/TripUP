package com.app.tripup.presentation.mainFlow.account.yourFavorites

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory
import com.app.tripup.presentation.mainFlow.explore.exploreMain.PlaceCard
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourFavoritesScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    favoritePlaces: List<Place> // Lista temporal de tus lugares favoritos para visualización
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp) // Tamaño típico de una topAppBar
            ) {
                // Imagen de fondo en la topAppBar
                Image(
                    painter = painterResource(id = R.drawable.montanafav), // Reemplaza con tu imagen
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Ajusta la imagen para que llene el espacio
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent),
                                startY = 0f,
                                endY = 100f
                            )
                        )
                )

                // Contenido de la topAppBar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp) 
                ) {
                    // Flecha para atrás
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            tint = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // Texto de título
                    Text(
                        text = stringResource(id = R.string.your_favorites),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    ) { innerPadding ->
        // Resto de la pantalla (grid de PlaceCards)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp), // Controla cuántas columnas muestra según el tamaño disponible
            contentPadding = innerPadding,
            modifier = Modifier.padding(8.dp)
        ) {
            items(favoritePlaces) { place ->
                PlaceCard(
                    place = place,
                    onClick = { /* Manejar clic en el lugar */ }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun YourFavoritesScreenPreviewLight() {
    MyApplicationTheme {
        // Lista temporal de favoritos para visualización
        val favoritePlaces = listOf(
            Place(1, "Favorite Place 1", "Location 1", "imageUrl", "Description", PlaceCategory.RESTAURANTS),
            Place(2, "Favorite Place 2", "Location 2", "imageUrl", "Description", PlaceCategory.HOTELS),
            Place(3, "Favorite Place 3", "Location 3", "imageUrl", "Description", PlaceCategory.DRINKS),
            Place(4, "Favorite Place 4", "Location 4", "imageUrl", "Description", PlaceCategory.ACTIVITIES)
        )
        YourFavoritesScreen(favoritePlaces = favoritePlaces)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun YourFavoritesScreenPreviewDark() {
    MyApplicationTheme {
        // Lista temporal de favoritos para visualización
        val favoritePlaces = listOf(
            Place(1, "Favorite Place 1", "Location 1", "imageUrl", "Description", PlaceCategory.RESTAURANTS),
            Place(2, "Favorite Place 2", "Location 2", "imageUrl", "Description", PlaceCategory.HOTELS),
            Place(3, "Favorite Place 3", "Location 3", "imageUrl", "Description", PlaceCategory.DRINKS),
            Place(4, "Favorite Place 4", "Location 4", "imageUrl", "Description", PlaceCategory.ACTIVITIES)
        )
        YourFavoritesScreen(favoritePlaces = favoritePlaces)
    }
}
