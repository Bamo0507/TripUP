package com.app.tripup.presentation.mainFlow.itinerary.itineraryCreation

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.R
import com.app.tripup.data.local.DatabaseModule
import com.app.tripup.data.repository.DayItineraryRepository
import com.app.tripup.data.repository.ItineraryRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ItineraryCreationRoute(
    onItineraryCreated: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    //Manejamos el context y creamos las instancias de los 2 repositorios que se necesitan
    val context = LocalContext.current
    val itineraryRepository = ItineraryRepository(
        DatabaseModule.getDatabase(context).itineraryDao()
    )
    val dayItineraryRepository = DayItineraryRepository(
        DatabaseModule.getDatabase(context).dayItineraryDao()
    )

    //Se crea una instancia del videmodel con los repositorios que se necesitan
    val viewModel: ItineraryCreationViewModel = viewModel(
        factory = ItineraryCreationViewModelFactory(itineraryRepository, dayItineraryRepository)
    )

    //Observamos el state
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Se lanza un effect para actualizar el state, esto se necesita para la navegación
    // Como de aquí necesitamos irnos a la pantalla de itinerary, y para eso necesitamos el id del itinerario
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            uiState.itineraryId?.let { id ->
                onItineraryCreated(id.toInt())
            }
        }
    }

    ItineraryCreationScreen(
        title = uiState.title,
        startDate = uiState.startDate,
        endDate = uiState.endDate,
        onTitleChanged = viewModel::onTitleChanged,
        onStartDateChanged = viewModel::onStartDateChanged,
        onEndDateChanged = viewModel::onEndDateChanged,
        onSaveItinerary = viewModel::onSaveItinerary,
        isFormComplete = uiState.isFormComplete,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryCreationScreen(
    title: String,
    startDate: String,
    endDate: String,
    onTitleChanged: (String) -> Unit,
    onStartDateChanged: (String) -> Unit,
    onEndDateChanged: (String) -> Unit,
    onSaveItinerary: () -> Unit,
    isFormComplete: Boolean,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.create_journey),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChanged,
                label = { Text(stringResource(id = R.string.itinerary_title)) },
                placeholder = { Text(stringResource(id=R.string.memorable_name)) },
                trailingIcon = {
                    if (title.isNotEmpty()) {
                        IconButton(onClick = { onTitleChanged("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),

            )

            //Se llama al método de datepickerfield mandandole el placeholder
            DatePickerField(
                label = stringResource(id = R.string.start_time),
                date = startDate,
                onDateSelected = onStartDateChanged
            )
            //Se llama al método de datepickerfield mandandole el placeholder
            DatePickerField(
                label = stringResource(id=R.string.end_time),
                date = endDate,
                onDateSelected = onEndDateChanged
            )
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd){
                Button(
                    modifier = Modifier.padding(vertical = 16.dp),
                    onClick = onSaveItinerary,
                    enabled = isFormComplete, // Habilitar el botón solo si el formulario está completo
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)
                    )
                ) {
                    Row {
                        Icon(Icons.Filled.Check, contentDescription = "Agregar")
                        Text(text = stringResource(id = R.string.complete_button), modifier = androidx.compose.ui.Modifier.padding(
                            start = 8.dp
                        ))
                    }
                }
            }

        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    date: String,
    onDateSelected: (String) -> Unit
) {
    //Se obtiene el contexto
    val context = LocalContext.current
    //Se indica el patrón de fecha que se necesita
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    //Guardamos la fecha
    val dateState = remember { mutableStateOf(date) }

    OutlinedTextField(
        value = dateState.value,
        onValueChange = {},
        label = { Text(label) },
        trailingIcon = {
            Row {
                if (dateState.value.isNotEmpty()) {
                    IconButton(onClick = { dateState.value = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
                IconButton(onClick = {
                    val initialDate = if (dateState.value.isNotEmpty()) {
                        LocalDate.parse(dateState.value, formatter)
                    } else {
                        LocalDate.now()
                    }
                    showDatePickerDialog(
                        context = context,
                        initialDate = initialDate,
                        onDateSelected = { selectedDate ->
                            val formattedDate = selectedDate.format(formatter)
                            dateState.value = formattedDate
                            onDateSelected(formattedDate)
                        }
                    )
                }) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Select Date",
                        tint = MaterialTheme.colorScheme.primary)
                }
            }
        },
        readOnly = true, //Se pone como read only, para que no bloque el .click que se le puso al icono
        modifier = Modifier.fillMaxWidth()
    )
}


fun showDatePickerDialog(
    context: android.content.Context,
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    //Abre un datepickerdialog
    val datePickerDialog = DatePickerDialog(
        context,
        //Se le pasa un lambda que recibe día, mes y año para construcir un LocalDate
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        initialDate.year,
        initialDate.monthValue - 1,
        initialDate.dayOfMonth
    )
    datePickerDialog.show() //muestra el dialogo en pantalla
}


@Preview(showBackground = true)
@Composable
fun ItineraryCreationScreenPreview(){
    ItineraryCreationScreen(
        title = "",
        startDate = "",
        endDate = "",
        onTitleChanged = {},
        onStartDateChanged = {},
        onEndDateChanged = {},
        onSaveItinerary = {},
        isFormComplete = true,
        onBackClick = {}
    )
}