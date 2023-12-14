package com.example.loginconfirebasecompose.model

import com.example.loginconfirebasecompose.view.LoginUIState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {
    override fun login(username: String, password: String): Flow<LoginUIState> = flow<LoginUIState> {
        try {
            emit(LoginUIState.Loading)
            val a = auth.signInWithEmailAndPassword(username, password).await()
            emit(LoginUIState.SignIn)
        } catch (e: Exception) {
            emit(LoginUIState.Error)
        }
    }

    override fun signOut() {
        auth.signOut()
    }


}