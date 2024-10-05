package com.app.tripup.presentation.login

interface LoginRepository {
    suspend fun login(email:String, password: String): Boolean
}