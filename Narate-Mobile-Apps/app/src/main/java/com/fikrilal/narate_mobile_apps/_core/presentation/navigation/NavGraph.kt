package com.ceritakita.app._core.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.RegisterScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationScreen.RegisterScreen.name) {
        composable(NavigationScreen.RegisterScreen.name) { RegisterScreen(navController) }
    }

}