package com.example.loginconfirebasecompose.view

sealed class LoginUIState {
    object Loading : LoginUIState()
    object SignIn : LoginUIState()
    object SignOut : LoginUIState()
    object Error: LoginUIState()
}
