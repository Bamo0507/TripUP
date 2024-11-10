package com.app.tripup.presentation.mainFlow.explore.locationInfo

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.tripup.R
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun LocationInfoRoute(
    placeId: Int,
    countryName: String,
    viewModel: LocationInfoViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(placeId, countryName) {
        //Actualizamos el state para manejar la info recibida y tener el Place a la mano
        viewModel.loadPlaceInfo(placeId, countryName)
    }

    LocationInfoScreen(
        state = state,
        onBackClick = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationInfoScreen(
    state: LocationInfoState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.place != null) {
                val place = state.place

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(24.dp)
                ) {
                    if (place.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = place.imageUrl,
                            contentDescription = place.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        )
                    } else {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {}
                    }
                }

                Text(
                    text = place.name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                Text(
                    text = place.location,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                )

                Text(
                    text = place.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(80.dp))
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.place_not_fount), color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun BottomButtons(modifier: Modifier = Modifier, state: LocationInfoState) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Button(
            //Se hace un Intent para buscar info del lugar en Google
            onClick = {
                //Creamos un intent para abrir la app de Google
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    val placeName = state.place?.name ?: "" //Obtenemos el lugar del lugar guardado en el state
                    //Se parse el Uri que es lo que se va a buscar, se mando la estructura básica de una búsqueda en google
                    //Después de $ se coloca lo que se quiere que se busque
                    data = Uri.parse("https://www.google.com/search?q=${Uri.encode(placeName)}")
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
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
