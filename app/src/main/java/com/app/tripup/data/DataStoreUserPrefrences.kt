// DataStoreUserPreferences.kt
package com.app.tripup.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para obtener el DataStore desde el contexto
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreUserPreferences(private val context: Context) : UserPreferences {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_NAME = stringPreferencesKey("user_name")
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    override suspend fun setUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    override fun getUserName(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: ""
        }
    }
}
