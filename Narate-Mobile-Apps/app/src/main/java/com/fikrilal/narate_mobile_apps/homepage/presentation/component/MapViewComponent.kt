package com.fikrilal.narate_mobile_apps.homepage.presentation.component

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story

@Composable
fun MapViewComposable(stories: List<Story>) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                onCreate(null)
                getMapAsync { googleMap ->
                    googleMap.uiSettings.isZoomControlsEnabled = true

                    // Menambahkan marker untuk setiap cerita
                    stories.forEach { story ->
                        val location = LatLng(story.lat, story.lon)
                        googleMap.addMarker(MarkerOptions().position(location).title(story.name).snippet(story.description))
                    }

                    // Opsional: Menyesuaikan posisi kamera
                    if (stories.isNotEmpty()) {
                        val firstStoryLocation = LatLng(stories.first().lat, stories.first().lon)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstStoryLocation, 10f))
                    }
                }
                mapView = this
            }
        },
        update = { mapView ->
            mapView?.onResume()
        }
    )

    DisposableEffect(context) {
        onDispose {
            mapView?.onDestroy()
        }
    }
}
