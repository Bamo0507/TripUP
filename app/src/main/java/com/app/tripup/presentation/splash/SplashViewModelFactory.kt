// SplashViewModelFactory.kt
package com.app.tripup.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripup.domain.UserPreferences

class SplashViewModelFactory(
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(userPreferences) as T
    }
}
