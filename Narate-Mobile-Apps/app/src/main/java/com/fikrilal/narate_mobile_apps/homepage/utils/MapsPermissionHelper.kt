package com.fikrilal.narate_mobile_apps.homepage.utils

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun MapsPermissionHandler(onPermissionGranted: @Composable () -> Unit) {
    val context = LocalContext.current
    val permissionGranted = remember { mutableStateOf(false) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                permissionGranted.value = true
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(key1 = true) {
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // Watch for changes in permissionGranted state
    if (permissionGranted.value) {
        onPermissionGranted()  // Call Composable when permission is granted
    }
}
