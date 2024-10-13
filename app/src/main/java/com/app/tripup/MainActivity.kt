package com.app.tripup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.app.tripup.presentation.login.LoginScreen
import com.app.tripup.presentation.login.LoginViewModel
import com.app.tripup.presentation.ui.theme.MyApplicationTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.app.tripup.data.source.ItineraryDb
import com.app.tripup.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    //private lateinit var db: ItineraryDb // Declarar la base de datos aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos la base de datos antes de establecer el contenido
        //db = ItineraryDb.getDatabase(this) // O puedes usar applicationContext si es necesario

        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Pasamos la base de datos al navigation
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        navController = navController
                        //db = db // Aquí pasamos la instancia de Room
                    )
                }
            }
        }
    }
}
