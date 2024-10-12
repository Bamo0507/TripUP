package com.app.tripup.presentation.mainFlow.account.accountPage

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.presentation.navigation.BottomNavigationBar
import com.app.tripup.presentation.ui.theme.MyApplicationTheme

@Composable
fun AccountRoute(onLogoutClick: () -> Unit){
    AccountScreen(onLogoutClick = onLogoutClick)
}


@Composable
fun AccountScreen(onLogoutClick: () -> Unit, modifier: Modifier = Modifier, userName: String = "Usuario 1") {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Fondo con la imagen 'citytemplate'
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Altura del fondo (ciudad)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.citytemplate),
                    contentScale = ContentScale.Crop, // Ajusta la imagen para que llene el espacio
                    contentDescription = "City background",
                    modifier = Modifier.fillMaxSize()
                )

                Spacer(modifier = Modifier.size(40.dp))

                // Avatar (imagen de usuario) centrado
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Alineación centrada y en la parte inferior del fondo
                        .offset(y = 60.dp), // Desplazamos hacia abajo para que sobresalga,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_avatar), // Reemplaza con la imagen del usuario
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape) // Borde blanco para que destaque
                    )
                    // Nombre del usuario
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Opciones de la cuenta (las opciones ya estaban correctas según tu comentario)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AccountOption(
                    icon = Icons.Default.Favorite,
                    label = stringResource(id = R.string.your_favorites),
                    iconTint = MaterialTheme.colorScheme.tertiaryContainer
                )
                //EN DEBATE DE SI SE AGREGARÁ
                // Spacer(modifier = Modifier.height(16.dp))
                /*
                AccountOption(
                    icon = Icons.Default.Send,
                    label = stringResource(id = R.string.past_trips),
                    iconTint = MaterialTheme.colorScheme.secondary
                )
                */

                Spacer(modifier = Modifier.height(16.dp))
                AccountOption(
                    icon = Icons.Default.ExitToApp,
                    label = stringResource(id = R.string.logout),
                    iconTint = MaterialTheme.colorScheme.primary,
                    navAction = {
                        onLogoutClick()
                    }

                )
            }
        }
    }
}


@Composable
fun AccountOption(icon: ImageVector, label: String, iconTint: Color, navAction: ()-> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navAction()
            }
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    MyApplicationTheme {
        AccountScreen(modifier = Modifier.fillMaxSize(), userName = "Usuario 1", onLogoutClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AccountScreenPreviewDark() {
    MyApplicationTheme {
        AccountScreen(modifier = Modifier.fillMaxSize(), userName = "Usuario 1", onLogoutClick = {})
    }
}


