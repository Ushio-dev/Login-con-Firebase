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

    private val _usernameErrorState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val usernameErrorState: StateFlow<Boolean>
        get() = _usernameErrorState


    fun login(username: String, password: String) {
        if (isValidText(username)) {
            viewModelScope.launch {
                authRepository.login(username, password).collect {
                    when (it) {
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
        } else {
            _usernameErrorState.value = true
        }
    }

    fun confirmError() {
        _uiState.value = LoginUIState.SignOut
    }

    fun isValidText(username: String): Boolean {
        // android.util.Patterns.EMAIL_ADDRESS.matcher(username) valida el email
        //return username.matches(Regex("[a-zA-Z]+")) && android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }
}