package com.fikrilal.narate_mobile_apps._core.presentation.component.appBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    onBackClicked: (() -> Unit)? = null,
    onOptionsClicked: (() -> Unit)? = null,
    backIcon: Int = R.drawable.ic_back,
    optionsIcon: Int = R.drawable.logo_narrate,
    iconSize: Dp = 24.dp,
    iconColor: Color = TextColors.grey500,
) {
    SmallTopAppBar(
        modifier = Modifier.padding(top = 16.dp),
        title = { },
        navigationIcon = {
            if (onBackClicked != null) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        painter = painterResource(id = backIcon),
                        contentDescription = "Back",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = optionsIcon),
                    contentDescription = "Logo",
                    modifier = Modifier.size(width = 100.dp, height = 28.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.White
        )
    )
    Divider(
        color = TextColors.grey200,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
}