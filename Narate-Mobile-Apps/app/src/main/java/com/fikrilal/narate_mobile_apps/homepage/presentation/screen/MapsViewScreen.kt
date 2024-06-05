package com.fikrilal.narate_mobile_apps.homepage.presentation.screen

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fikrilal.narate_mobile_apps.homepage.presentation.component.MapViewComposable
import com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel.HomeViewModel
import com.fikrilal.narate_mobile_apps.homepage.utils.MapsPermissionHandler

@Composable
fun MapsViewScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val storiesWithLocation by viewModel.storiesWithLocation.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    if (isLoading) {
        CircularProgressIndicator()
    } else if (error != null) {
        Text("Error: $error")
    } else {
        // Using the permission handler to manage map display based on location permission
        MapsPermissionHandler {
            MapViewComposable(stories = storiesWithLocation)
        }
    }
}