package com.example.loginconfirebasecompose.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginconfirebasecompose.viewmodel.LoginViewModel

@Composable
fun PrincipalScreen(viewModel: LoginViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido")
        Spacer(modifier = Modifier.height(15.dp))
        ElevatedButton(onClick = {
            viewModel.signOut()
            navController.navigate("loginScreen")
        }) {
            Text(text = "Salir")
        }
    }
}