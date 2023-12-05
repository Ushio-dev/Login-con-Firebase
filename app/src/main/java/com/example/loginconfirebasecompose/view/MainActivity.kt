package com.example.loginconfirebasecompose.view

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginconfirebasecompose.view.screens.LoginScreen
import com.example.loginconfirebasecompose.view.screens.PrincipalScreen
import com.example.loginconfirebasecompose.view.screens.SplashScreen
import com.example.loginconfirebasecompose.view.ui.theme.LoginConFirebaseComposeTheme
import com.example.loginconfirebasecompose.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window.decorView.post {
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val splashScreen = installSplashScreen()
        setContent {
            LoginConFirebaseComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = LoginViewModel()
                    navController.popBackStack()
                    NavHost(navController = navController, startDestination = "loginScreen") {
                        composable("loginScreen") { LoginScreen(navController, viewModel) }
                        composable("principalScreen") { PrincipalScreen(viewModel, navController) }
                        composable("splashScreen") { SplashScreen(navController) }
                    }
                }
            }
        }
    }
}
