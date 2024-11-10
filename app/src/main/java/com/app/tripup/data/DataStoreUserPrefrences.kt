package com.app.tripup.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.app.tripup.domain.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para obtener el DataStore desde el contexto
//Se establece un datastore con el nombre de extensión user_prefs
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

//Se le manda un context para acceder al datastore, devuelve las UserPreferences del usuario
class DataStoreUserPreferences(private val context: Context) : UserPreferences {
    //Se definen las KEYS con las que accederemos a las 3 cosas que se guardan en datastore
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val AVATAR_INDEX = intPreferencesKey("avatar_index")
    }

    //Se actualiza el valor de IS_LOGGED_IN de esa preferencia acorde a lo que se mande como parámetro
    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    //Se devuelve el valor de IS_LOGGED_IN como un Flow de tipo Boolean
    //Se usa map para transformar los datos de datastore, y devuelve false si no se encuentra nada
    override fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    //Se actualiza el valor de USER_NAME de esa preferencia acorde a lo que se mande como parámetro
    override suspend fun setUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    //Se devuelve el valor de USER_NAME como un Flow de tipo String
    //Si no se tiene nada (caso que no se espera ocurra, esto se dejó así previo a FireBase) devuelve Usuario
    override fun getUserName(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME]?.takeIf { it.isNotEmpty() } ?: "Usuario"
        }
    }

    //Se actualiza el valor de AVATAR_INDEX de esa preferencia acorde a lo que se mande como parámetro
    override suspend fun setAvatarIndex(avatarIndex: Int) {
        context.dataStore.edit { preferences ->
            preferences[AVATAR_INDEX] = avatarIndex
        }
    }

    //Se devuelve el valor que se tenga para el AVATAR_INDEZ como un Flow de tipo Int
    //Si no encuentra nada (primer inicio de sesión), devuelve 1 por defecto
    override fun getAvatarIndex(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[AVATAR_INDEX] ?: 1
        }
    }
}
