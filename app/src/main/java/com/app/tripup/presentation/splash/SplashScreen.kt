package com.app.tripup.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.tripup.R
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.login.LoginDestination
import com.app.tripup.presentation.mainFlow.MainNavigationGraph
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    navController: NavController,
    userPreferences: UserPreferences,
    modifier: Modifier = Modifier
) {
    val viewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(userPreferences)
    )
    SplashScreen(navController = navController, viewModel = viewModel, modifier = modifier)
}

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel,
    modifier: Modifier = Modifier
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = null)

    // Animación de escala del ícono
    var isVisible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.5f,
        animationSpec = androidx.compose.animation.core.tween(1000)
    )

    LaunchedEffect(isLoggedIn) {
        isVisible = true // Activa la animación
        delay(3000) // Duración de la Splash Screen

        if (isLoggedIn == true) {
            navController.navigate(MainNavigationGraph) {
                popUpTo(SplashDestination) { inclusive = true }
            }
        } else if (isLoggedIn == false) {
            navController.navigate(LoginDestination) {
                popUpTo(SplashDestination) { inclusive = true }
            }
        }
    }

    // UI de la Splash Screen
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícono de avión con animación de escala
            Image(
                imageVector = Icons.Default.Flight,
                contentDescription = "Airplane Icon",
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto de bienvenida
            Text(
                text = stringResource(id = R.string.splash_screen_title),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.splash_screen_subtitle),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Indicador de progreso circular
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
}


