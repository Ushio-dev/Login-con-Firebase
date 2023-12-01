package com.example.loginconfirebasecompose.model

import com.example.loginconfirebasecompose.view.LoginUIState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(username: String, password: String): Flow<LoginUIState>

    fun signOut()
}