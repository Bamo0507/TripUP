package com.app.tripup.presentation.mainFlow.explore.exploreMain

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.tripup.R
import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.tripup.presentation.ui.theme.MyApplicationTheme
import kotlinx.coroutines.withContext
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import java.io.IOException


@Composable
fun MainExploreRoute(
    viewModel: MainExploreViewModel = viewModel(),
    onPlaceClick: (Place) -> Unit,
    onNavigateToSpecific: (String) -> Unit
) {
    //Manejamos el context y el state
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Estado para almacenar el país actual (donde esté el usuario)
    var currentCountry by remember { mutableStateOf<String?>(null) }

    // Estado para controlar si ya hemos solicitado permisos - evitamos que se pida a cada rato
    var hasRequestedPermission by remember { mutableStateOf(false) }

    // Se genera un MANIFEST que establece el permiso que se necesita, en este caso la ubicación
    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    // Estado para controlar si el permiso está concedido
    val permissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                locationPermission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Lanza la solicitud de permiso al usuario
    // Dependiendo de lo que pase, se cambiará el valor de permissionGranted
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted.value = isGranted
        }
    )

    // Si el permiso no está concedido, solicitarlo
    //SideEffect se coloca para que se pida el permiso sin afectar el ciclo de la UI
    if (!permissionGranted.value && !hasRequestedPermission) {
        SideEffect {
            permissionLauncher.launch(locationPermission)
            hasRequestedPermission = true
        }
    }

    // Obtener la ubicación y el país actual cuando se conceda el permiso
    LaunchedEffect(permissionGranted.value) {
        if (permissionGranted.value) {
            //Se obtiene la ubicación actual
            val country = viewModel.getCurrentCountry(context)
            currentCountry = country
            //Si se obtiene el país, cargar los lugares (en el viewmodel se hace la búsqueda con el repositorio)
            if (country != null) {
                viewModel.loadPlacesForCountry(country)
            } else {
                // Si no se pudo obtener el país, cargar una lista vacía
                viewModel.loadPlacesForCountry("") // Esto cargará una lista vacía
            }
        } else {
            // Si no se concede el permiso, cargar una lista vacía por default
            viewModel.loadPlacesForCountry("") // Esto cargará una lista vacía
        }
    }

    ExploreScreen(
        state = state,
        onPlaceClick = onPlaceClick,
        onSearch = { query ->
            viewModel.onSearchQuerySubmitted(query) {
                onNavigateToSpecific(it)
            }
        }
    )
}

@Composable
fun ExploreScreen(
    state: MainExploreState,
    onPlaceClick: (Place) -> Unit,
    onSearch: (String) -> Unit
){
    var query by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de búsqueda
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { onSearch(query) }
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

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.data.isEmpty()) {
                // Mostrar imagen de respaldo
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notfound), // Reemplaza con tu imagen de respaldo
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(250.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.no_places_near_you),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                // Mostrar la lista de lugares
                LazyColumn {
                    val groupedPlaces = state.data.groupBy { it.categoryEnum }
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
                                        onClick = { onPlaceClick(place) },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search) //Establece que la acción es de búsqueda
            )
        }
    }
}

@Composable
fun PlaceCard(place: Place, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .size(200.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (place.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = place.imageUrl,
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Si no hay imagen, mostrar un color de fondo o imagen de respaldo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
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



