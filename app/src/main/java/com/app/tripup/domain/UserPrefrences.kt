package com.app.tripup.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    suspend fun setLoggedIn(isLoggedIn: Boolean)
    fun isLoggedIn(): Flow<Boolean>
    suspend fun setUserName(userName: String)
    fun getUserName(): Flow<String>
    suspend fun setAvatarIndex(avatarIndex: Int)
    fun getAvatarIndex(): Flow<Int>

}
