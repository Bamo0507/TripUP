package com.app.tripup.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseLoginRepository : LoginRepository {
    //Se genera una instancia de FirebaseAuth, objeto que maneja todas las operaciones de autenticación
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Realiza el inicio de sesión dedo un email y contraseña
    override suspend fun login(email: String, password: String): Boolean {
        //Se llama a firebase para que inicie sesión
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user != null //Si se encontró un usuario que concuerda con lo que se mando da true, si no false
    }

    //Registra un usuario con un email y contraseña
    override suspend fun register(email: String, password: String): Boolean {
        val result = auth.createUserWithEmailAndPassword(email, password).await() //Crea al usuario sobre la base de datos
        return result.user != null //Si logra crearlo devuelve true si no false
    }
}
