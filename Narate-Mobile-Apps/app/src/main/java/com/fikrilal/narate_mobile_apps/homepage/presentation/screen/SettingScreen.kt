package com.fikrilal.narate_mobile_apps.homepage.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.appBar.CustomAppBarWithLogo
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyLarge
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.AppColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps.auth.presentation.component.CardStoryComponent

@Composable
fun SettingScreen(
    navController: NavController,
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomAppBarWithLogo(onClick = {

            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 20.dp)
                .padding(WindowInsets.systemBars.asPaddingValues()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 1
                        drawLine(
                            color = TextColors.grey300,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyLarge(text = "Pengaturan Bahasa", color = TextColors.grey700)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "Arrow Right",
                        modifier = Modifier.size(24.dp),
                        tint = TextColors.grey700
                    )
                }
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(100),
                border = BorderStroke(1.dp, AppColors.errorColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = BrandColors.brandPrimary500
                )
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Logout",
                        modifier = Modifier.size(24.dp),
                        tint = AppColors.errorColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    LabelLarge("Logout", color = AppColors.errorColor)
                }
            }
        }
    }
}