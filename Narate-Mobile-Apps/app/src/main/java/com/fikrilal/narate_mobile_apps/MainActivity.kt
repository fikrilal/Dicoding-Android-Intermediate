package com.fikrilal.narate_mobile_apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fikrilal.narate_mobile_apps._core.presentation.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Your Jetpack Compose code to set up UI
            Navigation() // Call your composable navigation setup here
        }
    }
}