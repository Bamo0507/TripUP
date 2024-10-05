package com.app.tripup.presentation.login

import kotlinx.coroutines.delay

class LocalLoginRepository: LoginRepository {
    override suspend fun login(email: String, password: String): Boolean{
        delay(2000L)
        return email == "1" && password == "1"
    }
}