package com.app.tripup.presentation.mainFlow.account.accountPage

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun AccountRoute(
    onLogoutClick: () -> Unit,
    userPreferences: UserPreferences
) {
    AccountScreen(onLogoutClick = onLogoutClick, userPreferences = userPreferences)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onLogoutClick: () -> Unit,
    userPreferences: UserPreferences,
    userName: String = "Usuario"
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Fondo de la Ciudad con Avatar del Usuario
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Aumentar altura para mejorar la estética
            ) {
                Image(
                    painter = painterResource(id = R.drawable.citytemplate),
                    contentDescription = "City Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen del Avatar
                    Image(
                        painter = painterResource(id = R.drawable.user_avatar),
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    // Nombre del Usuario
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(68.dp))

            // Opciones de la Cuenta
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Opción de Log Out
                AccountOption(
                    icon = Icons.Default.ExitToApp,
                    label = stringResource(id = R.string.logout),
                    iconTint = MaterialTheme.colorScheme.primary
                ) {
                    coroutineScope.launch {
                        userPreferences.setLoggedIn(false)
                        onLogoutClick()
                    }
                }
            }
        }
    }
}
@Composable
fun AccountOption(
    icon: ImageVector,
    label: String,
    iconTint: Color,
    navAction: () -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navAction() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}

