package com.app.tripup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.app.tripup.data.DataStoreUserPreferences
import com.app.tripup.presentation.navigation.AppNavigation
import com.app.tripup.presentation.ui.theme.MyApplicationTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    //Instancia de DataStoreUserPreferences, lazy inicializa solo si es la primera vez que se accede a ella
    private val userPreferences by lazy {
        DataStoreUserPreferences(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Se inicializa Firebase en el app
        FirebaseApp.initializeApp(this)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //Se manda a llamar AppNavigation que contiene todo el navgraph principal
                    //Login, la splashscreen y lo principal del APP
                    AppNavigation(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        navController = navController,
                        userPreferences = userPreferences
                    )
                }
            }
        }
    }
}
