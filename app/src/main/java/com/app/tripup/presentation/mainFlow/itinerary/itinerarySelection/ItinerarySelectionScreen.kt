// ItinerarySelectionScreen.kt
package com.app.tripup.presentation.mainFlow.itinerary.itinerarySelection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BeachAccess
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.Surfing
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.IceSkating
import androidx.compose.material.icons.filled.Snowboarding
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.R
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.local.entities.DayItinerary
import com.app.tripup.data.repository.DayItineraryRepository
import com.app.tripup.data.repository.ItineraryRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

@Composable
fun ItinerarySelectionRoute(
    itineraryId: Int,
    onDaySelected: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val dayItineraryRepository = DayItineraryRepository(
        DatabaseModule.getDatabase(context).dayItineraryDao()
    )
    val itineraryRepository = ItineraryRepository(
        DatabaseModule.getDatabase(context).itineraryDao()
    )
    val viewModel: ItinerarySelectionViewModel = viewModel(
        factory = ItinerarySelectionViewModelFactory(dayItineraryRepository, itineraryRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDays(itineraryId)
    }

    ItinerarySelectionScreen(
        itineraryTitle = uiState.itineraryTitle,
        days = uiState.dayItineraries,
        onDaySelected = onDaySelected,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItinerarySelectionScreen(
    itineraryTitle: String,
    days: List<DayItinerary>,
    onDaySelected: (Int, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = itineraryTitle,
                    fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        if (days.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_days),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(days) { day ->
                    DayCard(
                        dayItinerary = day,
                        onClick = {
                            onDaySelected(day.id, itineraryTitle, day.date)
                        },
                        index = days.indexOf(day)
                    )
                }
            }
        }
    }
}

// Lista de íconos relacionados con actividades, viajes y ocio.
val dayIcons = listOf(
    Icons.Default.BeachAccess,       // Playa
    Icons.Default.Support,           // Apoyo / Asistencia
    Icons.Default.Surfing,           // Surf
    Icons.Default.Air,               // Aire libre
    Icons.Default.Diversity3,        // Grupos / Comunidad
    Icons.Default.DirectionsBoat,    // Bote / Navegación
    Icons.Default.DirectionsBike,    // Paseo en bicicleta
    Icons.Default.Hiking,            // Caminata / Senderismo
    Icons.Default.FlightTakeoff,     // Despegue de vuelo
    Icons.Default.Hotel,             // Hospedaje / Hotel
    Icons.Default.LocalDining,       // Restaurante / Comida
    Icons.Default.Nightlife,         // Vida nocturna
    Icons.Default.Event,             // Evento / Actividad
    Icons.Default.Festival,          // Festival
    Icons.Default.Map,               // Mapa / Ubicación
    Icons.Default.Train,             // Tren / Transporte
    Icons.Default.Park,              // Parque / Naturaleza
    Icons.Default.PhotoCamera,       // Fotografía / Turismo
    Icons.Default.Pool,              // Piscina / Natación
    Icons.Default.SportsSoccer,      // Deportes / Fútbol
    Icons.Default.SportsBasketball,  // Deportes / Básquetbol
    Icons.Default.SportsEsports,     // Videojuegos / Esports
    Icons.Default.Luggage,           // Equipaje / Maletas
    Icons.Default.CarRental,         // Renta de autos
    Icons.Default.Flight,            // Vuelo
    Icons.Default.LocalBar,          // Bar / Bebidas
    Icons.Default.TheaterComedy,     // Teatro / Entretenimiento
    Icons.Default.Spa,               // Spa / Relajación
    Icons.Default.MusicNote,         // Música / Conciertos
    Icons.Default.Castle,            // Castillo / Monumento histórico
    Icons.Default.Museum,            // Museo
    Icons.Default.Forest,            // Bosque / Naturaleza
    Icons.Default.Waves,             // Olas / Surf
    Icons.Default.Coffee,            // Café / Desayuno
    Icons.Default.LocalMall,         // Shopping / Compras
    Icons.Default.IceSkating,        // Patinaje / Actividades invernales
    Icons.Default.Snowboarding,      // Snowboarding / Nieve
    Icons.Default.Sailing,           // Vela / Navegación
    Icons.Default.Bed,               // Descanso / Dormir
    Icons.Default.ElectricScooter,   // Scooter / Movilidad urbana
    Icons.Default.Motorcycle,        // Motocicleta / Paseo
    Icons.Default.VideogameAsset,    // Videojuegos
    Icons.Default.Book,              // Lectura / Relax
)

// Función para obtener un ícono aleatorio.
fun getRandomIcon(): ImageVector {
    return dayIcons[Random.nextInt(dayIcons.size)]
}

@Composable
fun DayCard(
    dayItinerary: DayItinerary,
    index: Int,
    onClick: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("MMMM d", Locale.getDefault())
    val formattedDate = LocalDate.parse(dayItinerary.date).format(formatter)

    // Ícono basado en el índice del día.
    val icon = remember { getRandomIcon() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$formattedDate",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = icon,
                contentDescription = "Icon for $formattedDate",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
