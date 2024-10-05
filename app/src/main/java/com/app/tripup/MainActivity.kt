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
import com.app.tripup.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        navController = navController
                    )
                }
            }
        }
    }
}
