package com.fikrilal.narate_mobile_apps.homepage.presentation.screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.appBar.CustomAppBarWithLogo
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps.auth.presentation.component.CardStoryComponent
import com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val stories = viewModel.stories.collectAsState().value
    val scope = rememberCoroutineScope()
    Log.d("HomeScreen", "Displaying stories: $stories")

    // ANIMASI AGAR FAB MEMBESAR KETIKA DITEKAN
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 1.2f else 1f)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBarWithLogo(onClick = {
                navController.navigate("settingScreen")
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addNewStoryScreen") },
                modifier = Modifier
                    .padding(16.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                containerColor = BrandColors.brandPrimary500,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 4.dp
                ),
                interactionSource = interactionSource
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_quill_pen),
                    contentDescription = "Edit",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            items(stories) { story ->
                CardStoryComponent(
                    name = story.name,
                    date = story.createdAt,
                    description = story.description,
                    imageUrl = story.photoUrl,
                    onCardClick = {
                        navController.navigate("storyDetailsScreen/${story.id}")
                    }
                )
            }
        }
    }
}