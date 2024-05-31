package com.fikrilal.narate_mobile_apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.fikrilal.narate_mobile_apps._core.presentation.navigation.Navigation
import com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    val startDestination = if (authViewModel.isUserLoggedIn()) {
        "homeScreen"
    } else {
        "loginScreen"
    }

    Navigation(navController, startDestination)
}