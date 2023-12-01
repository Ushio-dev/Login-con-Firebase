package com.example.loginconfirebasecompose.view.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginconfirebasecompose.view.LoginUIState
import com.example.loginconfirebasecompose.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    when (viewModel.uiState.collectAsState().value) {
        LoginUIState.Error -> {
            AlertCredentialError(viewModel = viewModel)
        }

        LoginUIState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        LoginUIState.SignIn -> {
            navController.navigate("principalScreen")
        }

        LoginUIState.SignOut -> {
            var username by rememberSaveable {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }

            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Sign In")
                    UsernameField(username = username, onUsernameChange = {
                        username = it
                    }, viewModel.usernameErrorState.collectAsState().value)
                    Spacer(modifier = Modifier.height(15.dp))
                    PasswordField(
                        password = password,
                        onPasswordChange = { password = it },
                        viewModel.passwordErrorState.collectAsState().value
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    ConfirmButton(
                        viewModel = viewModel,
                        username = username,
                        password = password,
                        context = context
                    )

                }
            }
        }
    }
}

@Composable
fun AlertCredentialError(viewModel: LoginViewModel) {
    AlertDialog(
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(onClick = { viewModel.confirmError() }) {
                Text(text = "Aceptar")
            }
        },
        text = { Text(text = "Usuario o Contraseña incorrecto") },
        title = { Text(text = "Invalid Credentials") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(username: String, onUsernameChange: (String) -> Unit, isError: Boolean) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        value = username,
        onValueChange = onUsernameChange,
        label = { Text(text = "Email") },
        isError = isError,
    )

    if (isError) {
        Text(
            text = "Ingrese un usuario correcto",
            color = androidx.compose.ui.graphics.Color.Red,
            textAlign = TextAlign.Start
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit, isError: Boolean) {
    var isHide by remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        value = password,
        onValueChange = onPasswordChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isHide) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val iconImage = if (isHide) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (isHide) "show password" else "hide password"

            IconButton(onClick = { isHide = !isHide }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        label = { Text(text = "Contraseña") },
        isError = isError
    )
    if (isError) {
        Text(text = "La contraseña debe tener al menos 8 caracteres, 1 caracter numeros y 1 caracter alfanumericos")
    }
}

@Composable
fun ConfirmButton(viewModel: LoginViewModel, username: String, password: String, context: Context) {
    ElevatedButton(onClick = {
        viewModel.login(username, password)
    }) {
        Text(text = "Iniciar Sesion")
    }
}