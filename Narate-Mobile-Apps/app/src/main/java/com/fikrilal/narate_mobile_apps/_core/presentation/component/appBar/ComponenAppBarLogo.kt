package com.fikrilal.narate_mobile_apps._core.presentation.component.appBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBarWithLogo(
    onClickSettings: () -> Unit,
    onClickMaps: () -> Unit,
) {
    Box {
        Column(
        ) {
            TopAppBar(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                title = { },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_narrate),
                        contentDescription = "logo",
                        modifier = Modifier.size(height = 28.dp, width = 100.dp)
                    )
                },
                actions = {
                    Row {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .clip(CircleShape)
                                .border(1.dp, TextColors.grey300, CircleShape)
                        ) {
                            IconButton(onClick = onClickMaps) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_maps),
                                    contentDescription = "Maps",
                                    modifier = Modifier.size(24.dp),
                                    tint = TextColors.grey700
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(CircleShape)
                                .border(1.dp, TextColors.grey300, CircleShape)
                        ) {
                            IconButton(onClick = onClickSettings) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_setting),
                                    contentDescription = "Logout",
                                    modifier = Modifier.size(24.dp),
                                    tint = TextColors.grey700
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            Divider(
                color = TextColors.grey200,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
