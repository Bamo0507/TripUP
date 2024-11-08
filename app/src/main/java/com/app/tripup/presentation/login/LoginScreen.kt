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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.LoadingScreen
import com.app.tripup.presentation.login.LoginViewModel.Companion.Factory
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    userPreferences: UserPreferences
) {
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.Companion.Factory(
            loginRepository = FirebaseLoginRepository(),
            userPreferences = userPreferences
        )
    )

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    // Maneja la navegación de forma diferida
    LaunchedEffect(loginState.loginSuccess) {
        if (loginState.loginSuccess) {
            onLoginSuccess()
        }
    }

    LoginScreen(viewModel = viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

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

                // Campo de texto para el correo electrónico
                OutlinedTextField(
                    value = loginState.email,
                    onValueChange = { viewModel.onLoginEvent(LoginEvent.EmailChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.email_placeholder),
                            color = placeholderColor
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Email",
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
                        text = loginState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }



                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Iniciar Sesión
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

                Spacer(modifier = Modifier.height(8.dp))

                // Botón de Registrarse
                OutlinedButton(
                    onClick = { viewModel.onLoginEvent(LoginEvent.RegisterClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


