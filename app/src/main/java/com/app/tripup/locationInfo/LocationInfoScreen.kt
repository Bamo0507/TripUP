package com.app.tripup.locationInfo

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.app.tripup.R
import com.app.tripup.data.Place
import com.app.tripup.data.PlaceCategory
import com.app.tripup.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationInfoScreen(
    place: Place,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(place.isFavorite) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        bottomBar = {
            BottomButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(24.dp)

            ) {
                // Mostrar la imagen del lugar si está disponible, de lo contrario, usar un color de fondo
                if (place.imageUrl.isNotEmpty()) {

                } else {
                    // Fondo de color si no hay imagen
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ){

                    }

                }

                // Círculo con el corazón en el extremo inferior
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        // Actualizar el estado del lugar (lógica adicional puede ser necesaria)
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-16).dp, y = 28.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.favorite_button),
                        tint = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Nombre del lugar
            Text(
                text = place.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            // Ubicación del lugar
            Text(
                text = place.location,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
            )

            // Descripción del lugar
            Text(
                text = place.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(80.dp)) // Espacio antes de los botones
        }
    }
}

@Composable
fun BottomButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /* Acción de agregar */ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_button),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.add_button), color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Acción de buscar en línea */ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = stringResource(id = R.string.search_online_button),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.search_online_button), color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationInfoScreenPreview() {
    MyApplicationTheme {
        LocationInfoScreen(
            place = Place(
                id = 1,
                name = "Place Name",
                location = "Location",
                imageUrl = "", // Dejar vacío para usar color de fondo
                description = "This is a sample description of the place. It provides details about what to expect and why it's worth visiting.",
                category = PlaceCategory.RESTAURANTS,
                isFavorite = false
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationInfoScreenFavoritePreview() {
    MyApplicationTheme {
        LocationInfoScreen(
            place = Place(
                id = 1,
                name = "Place Name",
                location = "Location",
                imageUrl = "", // Dejar vacío para usar color de fondo
                description = "This is a sample description of the place. It provides details about what to expect and why it's worth visiting.",
                category = PlaceCategory.RESTAURANTS,
                isFavorite = true
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationInfoScreenDarkPreview() {
    MyApplicationTheme {
        LocationInfoScreen(
            place = Place(
                id = 1,
                name = "Place Name",
                location = "Location",
                imageUrl = "", // Dejar vacío para usar color de fondo
                description = "This is a sample description of the place. It provides details about what to expect and why it's worth visiting.",
                category = PlaceCategory.RESTAURANTS,
                isFavorite = false
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationInfoScreenFavoritePreviewDarkMode() {
    MyApplicationTheme {
        LocationInfoScreen(
            place = Place(
                id = 1,
                name = "Place Name",
                location = "Location",
                imageUrl = "", // Dejar vacío para usar color de fondo
                description = "This is a sample description of the place. It provides details about what to expect and why it's worth visiting.",
                category = PlaceCategory.RESTAURANTS,
                isFavorite = true
            )
        )
    }
}