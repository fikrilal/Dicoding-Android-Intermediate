package com.fikrilal.narate_mobile_apps._core.presentation.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.LoginScreen
import com.fikrilal.narate_mobile_apps.auth.presentation.screen.RegisterScreen
import com.fikrilal.narate_mobile_apps.homepage.presentation.screen.HomeScreen
import com.fikrilal.narate_mobile_apps.homepage.presentation.screen.SettingScreen
import com.fikrilal.narate_mobile_apps.story.presentation.screen.AddNewStory
import com.fikrilal.narate_mobile_apps.story.presentation.screen.StoryDetailsScreen

@Composable
fun Navigation(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("loginScreen") { CrossfadeAnimation { LoginScreen(navController) } }
        composable("registerScreen") { CrossfadeAnimation { RegisterScreen(navController) } }
        composable("homeScreen") { CrossfadeAnimation { HomeScreen(navController) } }
        composable("addNewStoryScreen") { CrossfadeAnimation { AddNewStory(navController) } }
        composable("storyDetailsScreen/{storyId}") { backStackEntry ->
            CrossfadeAnimation {
                val storyId = backStackEntry.arguments?.getString("storyId")
                if (storyId != null) {
                    StoryDetailsScreen(storyId = storyId, navController = navController)
                } else {
                    navController.navigate("homeScreen") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable("settingScreen") { CrossfadeAnimation { SettingScreen(navController) } }
    }
}

// ANIMASI UNTUK TRANSISI ANTAR PAGE
@Composable
fun CrossfadeAnimation(content: @Composable () -> Unit) {
    Crossfade(targetState = content) { screenContent ->
        screenContent()
    }
}
