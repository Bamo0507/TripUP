package com.app.tripup.presentation.login

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseLoginRepository : LoginRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Boolean {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user != null
    }

    override suspend fun register(email: String, password: String): Boolean {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user != null
    }
}
