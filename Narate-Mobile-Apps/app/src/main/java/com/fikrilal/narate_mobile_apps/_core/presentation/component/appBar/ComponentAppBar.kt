package com.fikrilal.narate_mobile_apps._core.presentation.component.appBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    onBackClicked: (() -> Unit)? = null,
    onOptionsClicked: (() -> Unit)? = null,
    backIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
    optionsIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.logo_narrate),
    iconSize: Dp = 24.dp,
    iconColor: Color = TextColors.grey500,
) {
    Box {
        Column {
            TopAppBar(
                title = { BodyLarge(text = title, color = TextColors.grey700, fontSize = 18.sp) },
                navigationIcon = onBackClicked?.let {
                    {
                        IconButton(onClick = it) {
                            Icon(
                                imageVector = backIcon,
                                contentDescription = "Back",
                                modifier = Modifier.size(iconSize),
                                tint = iconColor
                            )
                        }
                    }
                }!!,
                actions = {
                    if (onOptionsClicked != null) {
                        IconButton(onClick = onOptionsClicked) {
                            Icon(
                                imageVector = optionsIcon,
                                contentDescription = "Options",
                                modifier = Modifier.size(iconSize),
                                tint = iconColor
                            )
                        }
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
    }
}