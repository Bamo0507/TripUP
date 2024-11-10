package com.app.tripup.presentation.login

import android.util.Patterns
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.tripup.R
import com.app.tripup.data.repository.LoginRepository
import com.app.tripup.domain.UserPreferences
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    //Manejará el login y registro de usuarios, así como actualizará las preferencias del usuario
    private val loginRepository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // Se guarda el estado de las variabels indicadas en el STATE
    private val _loginState = MutableStateFlow(LoginState()) //Se actualiza de manera constante
    val loginState = _loginState.asStateFlow() //Permite obaservar cambios en loginState sin modificar nada

    //Lista de mensajes de error
    enum class DataError(
        @StringRes val text: Int
    ){
        NOT_FILLED(
            text = R.string.not_filled
        ),
        WRONG_USER_PASSWORD(
            text = R.string.wrong_user_or_password
        ),
        GENERIC_LOGIN_ERROR(
            text = R.string.generic_error
        ),
        WEAK_PASSWORD(
            text = R.string.the_password_must_at_least_have_6_characters
        ),
        ALREADY_EXISTS(
            text = R.string.an_account_with_this_email_already_exists
        ),
        WRONG_EMAIL_FORMAT(
            text = R.string.the_email_format_is_invalid
        ),
        GENERIC_REGISTER_ERROR(
            text = R.string.register_generic_error
        )
    }

    //Obtengo el mensaje de error
    fun getErrorMessage(context: Context, error: DataError): String {
        return context.getString(error.text)
    }


    //Listado de eventos de login
    fun onLoginEvent(event: LoginEvent, context: Context) {
        //When acrode al tipo de evento se manda a llamar a su respectivo método
        when (event) {
            LoginEvent.LoginClick -> onLogIn(context)
            LoginEvent.RegisterClick -> onRegister(context)
            is LoginEvent.EmailChanged -> onEmailChange(event.newEmail)
            is LoginEvent.PasswordChanged -> onPasswordChange(event.newPassword)
        }
    }

    /*
    onEmailChange y onPasswordChange actualizan el estado dl campo email y password
    se va actualizando constantemente con cada typeo que haga el usuario
     */
    private fun onEmailChange(email: String) {
        _loginState.update {
            it.copy(
                email = email,
                showError = false
            )
        }
    }
    private fun onPasswordChange(password: String) {
        _loginState.update {
            it.copy(
                password = password,
                showError = false
            )
        }
    }

    //Cuando se logea un usuario se llama a este método
    private fun onLogIn(context: Context) {
        viewModelScope.launch { //Se lanza la corrutina pues se hace algo muy ajeno a la UI
            //Guardamos el estado actual del loginState
            val screenState = _loginState.value

            //Vericamos primero que ambos campos estén llenos
            if (screenState.email.isEmpty() || screenState.password.isEmpty()) {
                _loginState.update { it.copy(showError = true, errorMessage = getErrorMessage(context, DataError.NOT_FILLED)) }
                return@launch
            }

            _loginState.update { it.copy(isLoading = true) }

            try {
                //Pasamos el método de login que está en el repositorio mandando lo que se tenga ahorita en el state
                val loggedStatus = loginRepository.login(screenState.email, screenState.password)
                //Se maneja si está o no loggeado (existe o no el usuario)
                if (loggedStatus) {
                    //Se actualizan las preferencias del usuario
                    userPreferences.setLoggedIn(true) //Actualizamos a true el estado de login
                    userPreferences.setUserName(screenState.email) //Actualizamos el nombre de usuario

                    //Actualizamos los nuvos valores del state (esto ya quedaría así cuando deslogueamos)
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
                            loginSuccess = true,
                            email = "",
                            password = "",
                        )
                    }
                } else {
                    //Si ya verificamos y es false
                    //Se quita la pantalla de carga y se muestra el error con su mensaje
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            errorMessage = getErrorMessage(context, DataError.WRONG_USER_PASSWORD)
                        )
                    }
                }
            } catch (e: Exception) {
                //Cualquier otro error que se pueda dar
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = getErrorMessage(context, DataError.GENERIC_LOGIN_ERROR)
                    )
                }
            }
        }
    }

    //Método para cuando nos registramos
    private fun onRegister(context: Context) {
        viewModelScope.launch { //Se lanza la corrutina para que no haya problema con la UI
            val screenState = _loginState.value //Se copia el state actual completo
            val emailPattern = Patterns.EMAIL_ADDRESS //Verifica el formato del email

            //Se verifica primero que ambos campos estén llenos
            if (screenState.email.isEmpty() || screenState.password.isEmpty()) {
                _loginState.update { it.copy(showError = true, errorMessage = getErrorMessage(context, DataError.NOT_FILLED)) }
                return@launch
            }

            //Verifica el formato del email
            if (!emailPattern.matcher(screenState.email).matches()) {
                _loginState.update { it.copy(showError = true, errorMessage = getErrorMessage(context, DataError.WRONG_EMAIL_FORMAT)) }
                return@launch
            }

            //Se verifica la longitud del password
            if (screenState.password.length < 6) {
                _loginState.update { it.copy(showError = true, errorMessage = getErrorMessage(context, DataError.WEAK_PASSWORD)) }
                return@launch
            }

            //Dejamos la pantalla en modo de carga
            _loginState.update { it.copy(isLoading = true) }

            //Probamos registrarnos en el repositorio
            try {
                //Pasamos el método de REGISTER que está en el repositorio mandando lo que se tenga ahorita en el state
                val registerStatus = loginRepository.register(screenState.email, screenState.password)
                if (registerStatus) {
                    //si se registró guardamos el correo y el login se pasa a true
                    userPreferences.setLoggedIn(true)
                    userPreferences.setUserName(screenState.email)

                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
                            loginSuccess = true,
                            email = "",
                            password = "",
                        )
                    }
                }
            }
            /*
            Manejo de posibles errores con FIREBASEAUTH
             */
            //Contraseña pobre
            catch (e: FirebaseAuthWeakPasswordException) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = getErrorMessage(context, DataError.WEAK_PASSWORD)
                    )
                }
            }
            //Formato de email incorrecto
            catch (e: FirebaseAuthInvalidCredentialsException) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = getErrorMessage(context, DataError.WRONG_EMAIL_FORMAT)
                    )
                }
            }
            //Ya existe un usuario con ese email
            catch (e: FirebaseAuthUserCollisionException) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = getErrorMessage(context, DataError.ALREADY_EXISTS)
                    )
                }
            }//Otro error
            catch (e: Exception) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = getErrorMessage(context, DataError.GENERIC_REGISTER_ERROR)
                    )
                }
            }

        }
    }

    //Creamos el Factory de ViewModels al tener que recibir parámetros en el ViewModel
    //El repositorio y las preferencias del usuario
    //Inyecta lo que necesita el LOGINVIEWMODEL al objeto viewmodel
    companion object {
        class Factory(
            private val loginRepository: LoginRepository,
            private val userPreferences: UserPreferences
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            //Se sobreescribe el método básico de crear un viewmodel para que tenga el repositorio y las preferencias
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(loginRepository, userPreferences) as T
            }
        }
    }

}

