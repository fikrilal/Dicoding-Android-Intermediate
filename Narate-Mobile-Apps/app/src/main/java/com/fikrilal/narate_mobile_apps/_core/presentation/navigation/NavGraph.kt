package com.fikrilal.narate_mobile_apps._core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.LoginScreen
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.RegisterScreen
import com.fikrilal.narate_mobile_apps.homepage.presentation.screen.HomeScreen
import com.fikrilal.narate_mobile_apps.story.presentation.screen.AddNewStory
import com.fikrilal.narate_mobile_apps.story.presentation.screen.StoryDetailsScreen

@Composable
fun Navigation(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("loginScreen") { LoginScreen(navController) }
        composable("registerScreen") { RegisterScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("addNewStoryScreen") { AddNewStory(navController) }
        composable("storyDetailsScreen/{storyId}") { backStackEntry ->
            // Extract the storyId from the backStackEntry
            val storyId = backStackEntry.arguments?.getString("storyId")
            if (storyId != null) {
                StoryDetailsScreen(storyId = storyId, navController = navController)
            } else {
                // Optionally handle the error or redirect if the parameter is missing
                // For simplicity here, just go back to home
                navController.navigate("homeScreen") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

