package com.app.tripup.domain

import kotlinx.coroutines.flow.Flow

//Serie de métodos para definir las preferencias del usuario
interface UserPreferences {
    suspend fun setLoggedIn(isLoggedIn: Boolean) //Establece si el usuario está logeado
    fun isLoggedIn(): Flow<Boolean> //Se escucha el estado de inicio de sesión del usuario
    suspend fun setUserName(userName: String) //Establece el nombre de usuario
    fun getUserName(): Flow<String> //Se escucha el nombre de usuario
    suspend fun setAvatarIndex(avatarIndex: Int) //Establece el índice del avatar que quiera el usuario
    fun getAvatarIndex(): Flow<Int> //Se escucha el índice del avatar que dejó el usuairo
}
