package com.fikrilal.narate_mobile_apps.homepage.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story
import com.fikrilal.narate_mobile_apps._core.presentation.component.appBar.CustomAppBarWithLogo
import com.fikrilal.narate_mobile_apps.auth.presentation.component.CardStoryComponent
import com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val stories = viewModel.stories.collectAsState().value

    Log.d("HomeScreen", "Displaying stories: $stories")

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBarWithLogo()
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
                        navController.navigate("detailScreen/${story.id}")
                    }
                )
            }
        }
    }
}