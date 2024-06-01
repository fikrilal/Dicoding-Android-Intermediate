package com.fikrilal.narate_mobile_apps.story.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.appBar.CustomAppBarWithLogo
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyLarge
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyMedium
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.dmSansFontFamily
import com.fikrilal.narate_mobile_apps.auth.presentation.component.CardStoryComponent
import com.fikrilal.narate_mobile_apps.story.presentation.component.CustomOutlinedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewStory(
    navController: NavController,
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBarWithLogo()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_image),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    LabelLarge(text = "Alexander Smith", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_globe),
                            contentDescription = "Globe Icon"
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        BodyMedium(text = "Everyone")
                    }
                }
            }

            var text by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 0.dp),
                value = text,
                onValueChange = { newText -> text = newText },
                textStyle = TextStyle(
                    fontFamily = dmSansFontFamily,
                    fontSize = 16.sp,
                    color = TextColors.grey700,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16 * 1.5.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                placeholder = { BodyLarge(text = "What's on your mind?") }
            )
            Image(
                painter = painterResource(id = R.drawable.sample_image),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomOutlinedButton(
                    text = "Gallery",
                    iconPainter = painterResource(id = R.drawable.ic_gallery),
                    onClick = {
                        // Aksi yang dijalankan ketika button diklik
                    },
                    iconDescription = "Gallery Icon",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomOutlinedButton(
                    text = "Camera",
                    iconPainter = painterResource(id = R.drawable.ic_camera),
                    onClick = {
                        // Aksi yang dijalankan ketika button diklik
                    },
                    iconDescription = "Camera Icon",
                    modifier = Modifier.weight(1f)
                )
            }


        }
    }
}