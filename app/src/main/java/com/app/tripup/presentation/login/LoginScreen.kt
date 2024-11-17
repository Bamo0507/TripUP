package com.app.tripup.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.app.tripup.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.tripup.domain.UserPreferences
import com.app.tripup.presentation.LoadingScreen
import com.app.tripup.data.repository.FirebaseLoginRepository

//Método route que llama el navgraphbuilder
@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    userPreferences: UserPreferences
) {
    //Se crea el viewmodel con ayuda del factory para poder pasarle el repository y las UserPreferences
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.Companion.Factory(
            loginRepository = FirebaseLoginRepository(),
            userPreferences = userPreferences
        )
    )

    //Se crea una variable que estará escuchando el LOGINSTATE
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

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
    // Se obtiene el contexto y el estado del loginState
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    // Estado para controlar la visibilidad de la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    //Si está cargando se muestra el loading screen
    if (loginState.isLoading) {
        LoadingScreen()
    }
    //Si no está cargando se muestra el login screen
    else {
        val placeholderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
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
                    onValueChange = { viewModel.onLoginEvent(LoginEvent.EmailChanged(it), context) },
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
                            color = placeholderColor,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = placeholderColor,
                        unfocusedBorderColor = if (loginState.showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la contraseña con el ícono de ojo
                OutlinedTextField(
                    value = loginState.password,
                    onValueChange = { viewModel.onLoginEvent(LoginEvent.PasswordChanged(it), context) },
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
                    // Se oculta la contraseña al principio
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) { //va pasando de true a false
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = placeholderColor,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), //LÍNEA para que se vea o no como una transformación visual
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = placeholderColor,
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
                    onClick = { viewModel.onLoginEvent(LoginEvent.LoginClick, context) },
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
                    onClick = { viewModel.onLoginEvent(LoginEvent.RegisterClick, context) },
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


