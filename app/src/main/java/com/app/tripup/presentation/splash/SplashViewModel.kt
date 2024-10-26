package com.app.tripup.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SplashViewModel(
    userPreferences: UserPreferences
) : ViewModel() {

    val isLoggedIn = userPreferences.isLoggedIn()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
