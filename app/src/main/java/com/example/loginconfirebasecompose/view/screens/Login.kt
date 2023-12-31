package com.example.loginconfirebasecompose.view.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.loginconfirebasecompose.view.LoginUIState
import com.example.loginconfirebasecompose.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    when (viewModel.uiState.collectAsState().value) {
        LoginUIState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                AlertDialog(
                    text = { Text(text = "Usuario o Contraseña incorrecto") },
                    title = { Text(text = "Invalid Credentials") },
                    onDismissRequest = {
                        viewModel.confirmError()
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.confirmError() }) {
                            Text(text = "Aceptar")
                        }
                    },
                )
            }

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
                        if (!it.equals(" ")) username = it
                    }, viewModel.usernameErrorState.collectAsState().value)
                    Spacer(modifier = Modifier.height(15.dp))
                    PasswordField(
                        password = password,
                        onPasswordChange = {
                            if (!it.equals(" ")) password = it
                        },
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
        text = { Text(text = "Usuario o Contraseña incorrecto") },
        title = { Text(text = "Invalid Credentials") },
        onDismissRequest = {
            viewModel.confirmError()
        },
        confirmButton = {
            TextButton(onClick = { viewModel.confirmError() }) {
                Text(text = "Aceptar")
            }
        },
    )
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
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    if (isError) {
        Text(
            text = "Ingrese un usuario correcto",
            color = androidx.compose.ui.graphics.Color.Red,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit, isError: Boolean) {
    var isHide by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
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
        isError = isError,
    )
    if (isError) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "La contraseña debe tener al menos 8 caracteres, 1 caracter numeros y 1 caracter alfanumericos",
            style = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            ),
            color = Color.Red
        )
    }
}

@Composable
fun ConfirmButton(viewModel: LoginViewModel, username: String, password: String, context: Context) {
    ElevatedButton(onClick = {
        //var email = username.replace("\\s".toRegex(), "")
        username.replace(" ","")
        viewModel.login(username, password)
    }) {
        Text(text = "Iniciar Sesion")
    }
}