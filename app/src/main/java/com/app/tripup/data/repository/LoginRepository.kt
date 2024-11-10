package com.app.tripup.data.repository

interface LoginRepository {
    //Se establecen los dos métodos que voy a necesitar login y register
    //Devuelven boolean para indicar si se pudo o no
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
}
