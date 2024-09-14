package com.app.tripup.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import com.app.tripup.R
import com.app.tripup.ui.theme.MyApplicationTheme

@Composable
fun ItineraryCard(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onCardClick() } // Detectar clic en la card
            .shadow(24.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        // Cambiar color de fondo y texto según el estado seleccionado
        val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseOnSurface
        val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor), // Fondo dinámico
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Título del itinerario
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                color = textColor // Color dinámico del texto
            )
            // Espacio
            Spacer(modifier = Modifier.weight(1f))
            // Imagen
            Box(modifier = Modifier.height(80.dp).width(88.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.travelingitinerary),
                    contentDescription = "Imagen",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ItineraryCardDemo() {
    // Estado que rastrea si la card está seleccionada
    var isSelected by remember { mutableStateOf(false) }

    Column {
        // Tarjeta interactiva
        ItineraryCard(
            title = "Family Trip",
            isSelected = isSelected,
            onCardClick = { isSelected = !isSelected } // Cambiar estado al hacer clic
        )
    }
}

@Composable
fun ItineraryCardPreview() {
    Column {
        // Vista de una tarjeta no seleccionada
        ItineraryCard(title = "Itinerario no seleccionado", isSelected = false, onCardClick = {})
        // Vista de una tarjeta seleccionada
        ItineraryCard(title = "Itinerario seleccionado", isSelected = true, onCardClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItineraryCard() {
    MyApplicationTheme {
        ItineraryCardPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItineraryCardDemo() {
    ItineraryCardDemo()
}
