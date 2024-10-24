package com.app.tripup.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import com.app.tripup.presentation.ui.theme.MyApplicationTheme
import android.content.res.Configuration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.LoadingScreen
import com.app.tripup.presentation.login.LoginViewModel.Companion.Factory

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    userPreferences: UserPreferences
) {
    // Crear una instancia del ViewModel utilizando el Factory
    val viewModel: LoginViewModel = viewModel(
        factory = Factory(
            loginRepository = LocalLoginRepository(),
            userPreferences = userPreferences
        )
    )

    // Escuchar cambios de estado del login
    val loginState by viewModel.loginState.collectAsState()

    // Si el login es exitoso, ejecutamos la navegación correspondiente
    if (loginState.loginSuccess) {
        onLoginSuccess()
    }

    // Pasar el ViewModel a la pantalla
    LoginScreen(viewModel = viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val loginState by viewModel.loginState.collectAsState()

    // Mostrar la LoadingScreen si el estado está en "isLoading"
    if (loginState.isLoading) {
        LoadingScreen()
    } else {
        val placeholderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        val errorMessage = stringResource(id = R.string.signup_error)

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loginlogo),
                    contentDescription = "Login Logo",
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de texto para el usuario
                OutlinedTextField(
                    value = loginState.email,
                    onValueChange = { viewModel.onLoginEvent(LoginEvent.EmailChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.signin_user),
                            color = placeholderColor
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Persona",
                            tint = placeholderColor
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la contraseña
                OutlinedTextField(
                    value = loginState.password,
                    onValueChange = { viewModel.onLoginEvent(LoginEvent.PasswordChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.signin_password),
                            color = placeholderColor
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = "Lock",
                            tint = placeholderColor
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                    )
                )

                // Mostrar mensaje de error si existe
                if (loginState.showError) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Sign-in
                Button(
                    onClick = { viewModel.onLoginEvent(LoginEvent.LoginClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.signin),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Botón de Google Sign-in (sin funcionalidad)
                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.googlelogo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.signup_google),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


