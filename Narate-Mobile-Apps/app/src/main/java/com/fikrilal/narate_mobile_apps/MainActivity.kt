package com.fikrilal.narate_mobile_apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fikrilal.narate_mobile_apps._core.presentation.navigation.Navigation
import com.fikrilal.narate_mobile_apps._core.presentation.theme.NarateMobileAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NarateMobileAppsTheme {
                Navigation()
            }
        }
    }
}