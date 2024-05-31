package com.fikrilal.narate_mobile_apps._core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.LoginScreen
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.RegisterScreen
import com.fikrilal.narate_mobile_apps.homepage.presentation.screen.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.RegisterScreen.name
    ) {
        composable(NavigationScreen.RegisterScreen.name) {
            RegisterScreen(navController)
        }
        composable(NavigationScreen.LoginScreen.name) {
            LoginScreen(navController)
        }
        composable(NavigationScreen.HomeScreen.name) {
            HomeScreen(navController)
        }
    }
}
