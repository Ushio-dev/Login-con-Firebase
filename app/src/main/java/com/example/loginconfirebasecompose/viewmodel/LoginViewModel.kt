package com.example.loginconfirebasecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginconfirebasecompose.model.AuthRepository
import com.example.loginconfirebasecompose.model.AuthRepositoryImpl
import com.example.loginconfirebasecompose.view.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _passwordErrorState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordErrorState: StateFlow<Boolean>
        get() = _passwordErrorState

    fun login(username: String, password: String) {
        _usernameErrorState.value = isValidText(username)
        _passwordErrorState.value = isValidPassword(password)

        if (!_usernameErrorState.value && !_passwordErrorState.value) {
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
        }
    }

    fun confirmError() {
        _uiState.value = LoginUIState.SignOut
    }

    private fun isValidText(username: String): Boolean {
        // android.util.Patterns.EMAIL_ADDRESS.matcher(username) valida el email
        //return username.matches(Regex("[a-zA-Z]+")) && android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }

        val isThereAnyNumber = password.any { it.isDigit() }
        if (!isThereAnyNumber) {
            return false
        }

        val isThereAnyNoAlphaNumer = password.any { it.isLetterOrDigit() }
        if (!isThereAnyNoAlphaNumer) {
            return false
        }

        return true
    }


    fun signOut() {
        try {
            _uiState.value = LoginUIState.SignOut
            authRepository.signOut()
        } catch (e: Exception) {
            _uiState.value = LoginUIState.Error
        }
    }
}