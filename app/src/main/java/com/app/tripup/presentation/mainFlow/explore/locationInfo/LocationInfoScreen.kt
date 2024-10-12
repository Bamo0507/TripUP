package com.app.tripup.presentation.mainFlow.explore.locationInfo

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
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
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.R
import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory
import androidx.compose.ui.platform.LocalContext
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun LocationInfoRoute(
    placeId: Int,
    viewModel: LocationInfoViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    fromSearchDb: Boolean,
) {
    // Obtenemos el estado de la UI del ViewModel
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(placeId) {
        viewModel.loadPlaceInfo(placeId, fromSearchDb) // Pasa ambos parámetros
    }


    // Pasamos el estado a la pantalla
    LocationInfoScreen(
        state = state,
        onBackClick = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationInfoScreen(
    state: LocationInfoState,  // Estado que contiene la información del lugar
    onBackClick: () -> Unit,   // Navegación al hacer clic en el botón "Atrás"
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(state.place?.isFavorite ?: false) }

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
                    .padding(16.dp),
                state = state
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            if (state.isLoading) {
                // Mostrar un indicador de carga si los datos se están cargando
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.place != null) {
                // Mostrar los detalles del lugar si los datos ya están disponibles
                val place = state.place

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(24.dp)
                ) {
                    // Mostrar la imagen del lugar si está disponible, de lo contrario, usar un color de fondo
                    if (place.imageUrl.isNotEmpty()) {
                        // AQUÍ TENDRÉ QUE COLOCAR LA IMAGEN CUANDO SE TENGA


                    } else {
                        // Fondo de color si no hay imagen
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {}
                    }

                    // Círculo con el corazón en el extremo inferior
                    //SE EVALUARÁ SU INCORPORACIÓN
                    /*
                    IconButton(
                        onClick = {
                            isFavorite = !isFavorite
                            // Lógica adicional si es necesario para actualizar en el repositorio
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

                     */

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
            } else {
                // Mostrar un mensaje de error si no se encuentra el lugar
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Lugar no encontrado", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun BottomButtons(modifier: Modifier = Modifier, state: LocationInfoState) {
    // Obtener el contexto de la aplicación desde el entorno composable
    val context = LocalContext.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Botón de agregar (actualmente comentado)
        /*
        Button(
            onClick = { /* Acción de agregar, por ahora no funcional */ },
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
        */

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Crear el Intent con ACTION_VIEW para abrir la URL en un navegador
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    // Verificar que el nombre del lugar no sea nulo antes de intentar usarlo
                    val placeName = state.place?.name ?: ""
                    data = Uri.parse("https://www.google.com/search?q=${Uri.encode(placeName)}")
                }
                // Usar el contexto obtenido previamente para iniciar la actividad
                context.startActivity(intent)
            },
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
        // Simulación de un lugar cargado
        val sampleState = LocationInfoState(
            isLoading = false,
            place = Place(
                id = 1,
                name = "Place Name",
                location = "Location",
                imageUrl = "", // Simulación sin imagen
                description = "This is a sample description of the place. It provides details about what to expect and why it's worth visiting.",
                category = PlaceCategory.RESTAURANTS,
                isFavorite = false
            )
        )
        LocationInfoScreen(state = sampleState, onBackClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LocationInfoScreenDarkPreview() {
    MyApplicationTheme(darkTheme = true) {
        // Simulación de un lugar cargado en modo oscuro
        val sampleState = LocationInfoState(
            isLoading = false,
            place = Place(
                id = 1,
                name = "Place Name",
                location = "Location",
                imageUrl = "", // Simulación sin imagen
                description = "This is a sample description of the place. It provides details about what to expect and why it's worth visiting.",
                category = PlaceCategory.RESTAURANTS,
                isFavorite = true
            )
        )
        LocationInfoScreen(state = sampleState, onBackClick = {})
    }
}

