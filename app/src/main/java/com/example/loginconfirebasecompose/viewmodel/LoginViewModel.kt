package com.example.loginconfirebasecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginconfirebasecompose.model.AuthRepository
import com.example.loginconfirebasecompose.model.AuthRepositoryImpl
import com.example.loginconfirebasecompose.view.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState.SignOut)

    val uiState: StateFlow<LoginUIState>
        get() = _uiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            authRepository.login(username, password).collect {
                when(it) {
                    LoginUIState.Error -> {
                        _uiState.value = it
                    }
                    LoginUIState.Loading -> {
                        _uiState.value = it
                    }
                    LoginUIState.SignIn -> {
                        _uiState.value = it
                    }
                    LoginUIState.SignOut -> {
                        _uiState.value = it
                    }
                }
            }
        }
    }

    fun confirmError() {
        _uiState.value = LoginUIState.SignOut
    }
}