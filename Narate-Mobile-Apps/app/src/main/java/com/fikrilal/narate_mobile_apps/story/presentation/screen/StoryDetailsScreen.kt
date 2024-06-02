package com.fikrilal.narate_mobile_apps.story.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyLarge
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyMedium
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps.story.presentation.component.AppBarStoryDetail
import com.fikrilal.narate_mobile_apps.story.presentation.viewmodel.StoryDetailsViewModel

@Composable
fun StoryDetailsScreen(
    storyId: String,
    navController: NavController
) {

    val viewModel: StoryDetailsViewModel = hiltViewModel()
    val storyDetail = viewModel.storyDetail.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchStoryDetail(storyId)
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppBarStoryDetail(
                text = "Detail Story",
                onClick = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            storyDetail.value?.let { story ->
                Column {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(WindowInsets.systemBars.asPaddingValues()),
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = story.photoUrl),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            LabelLarge(text = story.name, fontSize = 18.sp)
                            BodyMedium(text = story.createdAt)
                        }
                    }
                    BodyLarge(
                        text = story.description,
                        color = TextColors.grey700,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = story.photoUrl),
                        contentDescription = "Post Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        BodyLarge(text = "Memuat cerita...")
                    }
                }
            }
        }
    }
}
